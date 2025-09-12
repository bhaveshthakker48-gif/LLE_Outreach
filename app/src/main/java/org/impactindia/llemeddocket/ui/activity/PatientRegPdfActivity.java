package org.impactindia.llemeddocket.ui.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.DatabaseHelper;
import org.impactindia.llemeddocket.R;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.PatientDAO;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.ui.fragment.PatientRegFrag;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientRegPdfActivity extends BaseActivity implements OnClickListener {

    private SQLiteDatabase db;
    private PatientDAO patientDAO;
    private Bundle args;
    private String regNo;
    private boolean skipPdf;
    private Long userId, pId;
    private Patient patientData;
    private Button btnYes, btnNo;
    private TextView lblDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_pdf);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        WindowInsetsControllerCompat insetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (insetsController != null) {
            insetsController.setAppearanceLightStatusBars(true);
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content),
                new OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        view.setPadding(
                                systemBars.left,
                                systemBars.top,
                                systemBars.right,
                                systemBars.bottom
                        );
                        return insets;
                    }
                });

        args = getIntent().getExtras();
        regNo = args.getString(PatientRegFrag.REG_NO);
        pId = (Long) args.getSerializable(PatientRegFrag.P_ID);
        skipPdf = (boolean) args.getSerializable(PatientRegFrag.SKIP_PDF);
        userId = getApp().getSettings().getUserId();
        db = new DatabaseHelper(this).getReadableDatabase();
        patientDAO = new PatientDAO(this, db);
        lblDesc = (TextView) findViewById(R.id.lblDesc);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = (Button) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);

        if (!skipPdf) {
            try {
                if (regNo != null) {
                    patientData = patientDAO.findFirstByField(Patient.REG_NO, regNo);
                } else {
                    patientData = patientDAO.findByPrimaryKey(pId);
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }


            String state = Environment.getExternalStorageState();
            if (!Environment.MEDIA_MOUNTED.equals(state)) {
                Toast.makeText(this,
                        R.string.sd_card_unavailable,
                        Toast.LENGTH_SHORT).show();
            }
            File docDir;
            docDir = new File(Environment.getExternalStorageDirectory(), "Reports");
            if (!docDir.exists()) {
                docDir.mkdir();
            }
            SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.PRESCRIPTION_DATE_FORMAT);
            File pdfFile;
            if (regNo != null) {
                pdfFile = new File(docDir, "CL_Reg_" + regNo + "_" + sdf.format(Calendar.getInstance().getTime()) + "-LLE" + ".pdf");
            } else {
                pdfFile = new File(docDir, "CL_Reg_" + pId + "_" + sdf.format(Calendar.getInstance().getTime()) + "-LLE" + ".pdf");
            }

            a4Template(pdfFile);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goBack() {
        // Enable Up button only if there are entries in the back stack
        boolean canBack = getFragmentManager().getBackStackEntryCount() > 0;
        if (canBack) {
            // This method is called when the up button is pressed. Just the pop back stack;
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void gotoScreen(Class<?> cls, Long userId) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(PatientRegFrag.USER_ID, userId);
        startActivity(intent);
    }

    private void aletterTemplate(File pdfFile) {
        a4Template(pdfFile);
    }

    private void a4Template(File pdfFile) {
        try {
            Document document = new Document();
            Rectangle pageSize = PageSize.A4;
            document.setPageSize(pageSize);
            document.setMargins(72.0F, 72.0F, 180.0F, 36.0F);
//			document.setMargins(letterHeadData.getLeftMargin(), letterHeadData.getRightMargin(), letterHeadData.getTopMargin(), letterHeadData.getBottomMargin());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            PdfHeaderFooter headerFooter = new PdfHeaderFooter();
            writer.setPageEvent(headerFooter);

            document.open();
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            int titleFont = 16;
//			int paraFont = 14;
            String boldStyle = "bold";
            String normalStyle = "normal";

            PdfPTable medIdTable = new PdfPTable(3);
            medIdTable.setWidthPercentage(100);
            medIdTable.addCell(getCell(isEmpty(patientData.getRegNo()) ? "" : "Reg. No.: " + patientData.getRegNo(), Element.ALIGN_LEFT, paraFont));
            medIdTable.addCell(getCell(isEmpty(patientData.getAadharNo()) ? "" : "Aadhar No.: " + patientData.getAadharNo(), Element.ALIGN_RIGHT, paraFont));
            SimpleDateFormat targetDF;
            targetDF = new SimpleDateFormat(AttributeSet.Constants.SHORT_DATE_FORMAT);
            Date regDate = Calendar.getInstance().getTime();
            String assesmentDate = targetDF.format(regDate);
            medIdTable.addCell(getCell(isEmpty(assesmentDate) ? "" : "Date: " + assesmentDate, Element.ALIGN_RIGHT, paraFont));
            document.add(medIdTable);

            document.add(new Chunk(Chunk.NEWLINE));

            PdfPTable nameTable = new PdfPTable(2);
            nameTable.setWidthPercentage(100);
            StringBuilder nameBuilder = new StringBuilder();
            if (!isEmpty(patientData.getfName())) {
                nameBuilder.append(patientData.getfName());
                nameBuilder.append(" ");
            }
            ;
            if (!isEmpty(patientData.getmName())) {
                nameBuilder.append(patientData.getmName());
                nameBuilder.append(" ");
            }
            ;
            if (!isEmpty(patientData.getlName())) {
                nameBuilder.append(patientData.getlName());
            }
            ;
            nameTable.addCell(getCell(isEmpty(nameBuilder.toString()) ? "" : "Name: " + nameBuilder.toString(), Element.ALIGN_LEFT, paraFont));
            StringBuilder ageGenderBuilder = new StringBuilder();
            if (!isEmpty(String.valueOf(patientData.getAge()))) {
                ageGenderBuilder.append("Age: " + String.valueOf(patientData.getAge()) + " " + patientData.getAgeUnit());
                ageGenderBuilder.append("            ");
            }
            if (!isEmpty(String.valueOf(patientData.getGender()))) {
                ageGenderBuilder.append("Gender: " + patientData.getGender());
            }
            nameTable.addCell(getCell(isEmpty(ageGenderBuilder.toString()) ? "" : ageGenderBuilder.toString(), Element.ALIGN_RIGHT, paraFont));
            document.add(nameTable);
            document.add(new Chunk(Chunk.NEWLINE));

            PdfPTable addressTable = new PdfPTable(1);
            addressTable.setWidthPercentage(100);
            StringBuilder addBuider = new StringBuilder();
            if (!isEmpty(patientData.getHouseNameNo())) {
                addBuider.append(patientData.getHouseNameNo());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getStreetName())) {
                addBuider.append(patientData.getStreetName());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getLocalityAreaPada())) {
                addBuider.append(patientData.getLocalityAreaPada());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getCityTownVillage())) {
                addBuider.append(patientData.getCityTownVillage());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getTaluka())) {
                addBuider.append(patientData.getTaluka());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getDistrict())) {
                addBuider.append(patientData.getDistrict());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getStateName())) {
                addBuider.append(patientData.getStateName());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getPinCode())) {
                addBuider.append(patientData.getPinCode());
                addBuider.append(",");
                addBuider.append(" ");
            }
            ;
            if (!isEmpty(patientData.getCountryName())) {
                addBuider.append(patientData.getCountryName());
            }
            ;
            addressTable.addCell(getCell(isEmpty(addBuider.toString()) ? "" : "Address: " + addBuider.toString(), Element.ALIGN_LEFT, paraFont));
            document.add(addressTable);

            document.add(new Chunk(Chunk.NEWLINE));

            PdfPTable contactTable = new PdfPTable(2);
            contactTable.setSpacingAfter(5);
            contactTable.setWidthPercentage(100);
            StringBuilder contactBuilder = new StringBuilder();
            if (!isEmpty(patientData.getMobileNo())) {
                contactBuilder.append(patientData.getMobileNo());
            }
            if (!isEmpty(patientData.getResidenceNo())) {
                if (!contactBuilder.toString().isEmpty()) {
                    contactBuilder.append(",");
                    contactBuilder.append(" ");
                }
                contactBuilder.append(patientData.getResidenceNo());
            }
            contactTable.addCell(getCell(isEmpty(contactBuilder.toString()) ? "" : "Contact Number: " + contactBuilder.toString(), Element.ALIGN_LEFT, paraFont));
            contactTable.addCell(getCell(isEmpty(patientData.getTagName()) ? "" : "Tags: " + patientData.getTagName(), Element.ALIGN_LEFT, paraFont));
            document.add(contactTable);

            document.add(new Paragraph("\n"));

            PdfPTable weightBpTable = new PdfPTable(2);
            weightBpTable.setWidthPercentage(100);
            weightBpTable.addCell(getCell("Weight: " + "_____kgs", Element.ALIGN_LEFT, paraFont));
            weightBpTable.addCell(getCell("Blood Pressure: " + "_____ / _____ mm Hg", Element.ALIGN_RIGHT, paraFont));
            document.add(weightBpTable);

            document.add(new Paragraph("\n"));

            PdfPTable bloodGlucoseTable = new PdfPTable(1);
            bloodGlucoseTable.setWidthPercentage(100);
            bloodGlucoseTable.addCell(getCell("Blood Glucose Random: " + "_____mg/dL", Element.ALIGN_RIGHT, paraFont));
            document.add(bloodGlucoseTable);

            document.add(new Paragraph("\n"));

            PdfPTable notesTable = new PdfPTable(2);
            notesTable.setWidthPercentage(100);
            notesTable.addCell(getCell("Notes: ", Element.ALIGN_LEFT, paraFont));
            notesTable.addCell(getCell(isEmpty(patientData.getReferredBy()) ? "" : "Referred by: " + patientData.getReferredBy(), Element.ALIGN_RIGHT, paraFont));
            document.add(notesTable);

            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            Drawable signDrawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.checkbox);
            BitmapDrawable signBitDW = (BitmapDrawable) signDrawable;
            Bitmap signBmp = signBitDW.getBitmap();
//				Bitmap signBmp = BitmapFactory.decodeFile(letterHeadStyleData.getSignatureImg().trim());
            ByteArrayOutputStream checkboxOS = new ByteArrayOutputStream();
            signBmp.compress(CompressFormat.PNG, 100, checkboxOS);
            Image checkboxImg = Image.getInstance(checkboxOS.toByteArray());
            checkboxImg.setAlignment(Element.ALIGN_RIGHT);


            PdfPTable diagTable = new PdfPTable(7);
            diagTable.setWidthPercentage(80);
            PdfPCell diagCell = getCell("Diagnosis:", Element.ALIGN_LEFT, Element.ALIGN_CENTER, paraFont);
            diagCell.setColspan(3);
            diagTable.addCell(diagCell);
//			diagTable.addCell(getCell("", Element.ALIGN_LEFT, paraFont));
            diagTable.addCell(getCell(checkboxImg, Element.ALIGN_RIGHT, Element.ALIGN_CENTER));
            diagTable.addCell(getCell(" OPD", Element.ALIGN_LEFT, Element.ALIGN_CENTER, paraFont));
            diagTable.addCell(getCell(checkboxImg, Element.ALIGN_RIGHT, Element.ALIGN_CENTER));
            diagTable.addCell(getCell(" Surgery", Element.ALIGN_LEFT, Element.ALIGN_CENTER, paraFont));
            document.add(diagTable);

            document.close();
            viewPdf(pdfFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class PdfHeaderFooter extends PdfPageEventHelper {
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            super.onStartPage(writer, document);
            try {
                Drawable headerImgDW = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.impact_foundation);
                BitmapDrawable headerBmpDW = (BitmapDrawable) headerImgDW;
                Bitmap headerBmp = headerBmpDW.getBitmap();
                ByteArrayOutputStream headerImgOS = new ByteArrayOutputStream();
                headerBmp.compress(CompressFormat.PNG, 100, headerImgOS);
                Image headerImg = Image.getInstance(headerImgOS.toByteArray());
                headerImg.setAlignment(Element.ALIGN_CENTER);

                PdfPTable headerImgTable = new PdfPTable(4);
                headerImgTable.setTotalWidth(1000F);
                headerImgTable.addCell(getCell(headerImg, Element.ALIGN_CENTER, Element.ALIGN_CENTER));
                headerImgTable.addCell(getCell(headerImg, Element.ALIGN_CENTER, Element.ALIGN_CENTER));
                headerImgTable.addCell(getCell(headerImg, Element.ALIGN_CENTER, Element.ALIGN_CENTER));
                headerImgTable.addCell(getCell(headerImg, Element.ALIGN_CENTER, Element.ALIGN_CENTER));

                headerImgTable.writeSelectedRows(0, -1, 0, 36, writer.getDirectContent());
                document.add(headerImgTable);
            } catch (BadElementException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            super.onEndPage(writer, document);
        }
    }

    public Font getFont(String fontName, Integer fontSize, String fontStyle) {
        int font;
        String fontType;
        if (fontStyle.equals("bold") || fontStyle.equals("bolder")) {
            font = Font.BOLD;
        } else {
            font = Font.NORMAL;
        }
        if (fontName.equals("Courier")) {
            fontType = FontFactory.COURIER;
            if (font == Font.NORMAL) {
                fontType = FontFactory.HELVETICA;
            }
        } else if (fontName.equals("Helvetica")) {
            fontType = FontFactory.HELVETICA;
        } else if (fontName.equals("Times New Roman")) {
            fontType = FontFactory.TIMES_ROMAN;
        } else {
            fontType = FontFactory.HELVETICA;
        }
        Font textFont = FontFactory.getFont(fontType, fontSize, font);
        return textFont;
    }

    public PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setLeading(0, 2);
        return cell;
    }

    public PdfPCell getCell(String text, int alignment, int alignmentVert, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(alignmentVert);
        cell.setBorder(PdfPCell.NO_BORDER);
//		cell.setLeading(0, 2);
        return cell;
    }

    public PdfPCell getCell(Image img, int alignment, int alignmentVert) {
        PdfPCell cell = new PdfPCell(img);
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(alignmentVert);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setLeading(0, 2);
        return cell;
    }
	

    public void viewPdf(File pdfFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("content://" + getPackageName() + "/" + pdfFile.getAbsolutePath());
            intent.setDataAndType(uri, "application/pdf");
            Intent intentChooser = Intent.createChooser(intent, "Choose pdf application");
//			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intentChooser);
        } catch (ActivityNotFoundException exception) {
            showPdfViewerNotAvailableDialog();
        } catch (Exception exception) {
            showPdfViewerNotAvailableDialog();
        }
    }

    public void showPdfViewerNotAvailableDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(R.string.pdf_reader_not_installed);
        alertDialog.setPositiveButton(R.string.btn_lbl_install_now, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://details?id=" + "com.adobe.reader");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException exception) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader")));
                }
            }
        });
        AlertDialog alert = alertDialog.create();
//		alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        alert.show();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnYes) {
            gotoScreen(PatientActivity.class, userId);
            finish();
        } else if (viewId == R.id.btnNo) {
            finish();
        }

    }
}
