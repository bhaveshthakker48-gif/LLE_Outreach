package org.impactindia.llemeddocket;

public class AttributeSet {

    public static class Params {

    public static final String SERVICE_NAMESPACE = "http://impactindiafoundation.org/ImpactWebService/rest/";
   /* Local server */
//          public static final String SERVICE_NAMESPACE = "http://192.168.0.160:8091/ImpactWebService/rest/";


        public static final String READ = "impactwebcall/";
        public static final String UPDATE = "impactwebcall/";

        public static final String GET_LOGIN_DETAIL = SERVICE_NAMESPACE + READ + "GetLogindetail";
        public static final String GET_CAMP_DETAIL = SERVICE_NAMESPACE + READ + "GetCampdetail";
        public static final String GET_TAG_DETAIL = SERVICE_NAMESPACE + READ + "GetTagdetail";
        public static final String GET_SUB_TAG_DETAIL = SERVICE_NAMESPACE + READ + "GetSubtagMasterList";
        public static final String GET_STATE_DETAIL = SERVICE_NAMESPACE + READ + "GetStateDetail";
        public static final String GET_CAMP_PATIENT_LIST = SERVICE_NAMESPACE + READ + "GetCampPatientList";
        public static final String GET_BLOOD_PRESSURE_LIST = SERVICE_NAMESPACE + READ + "GetBloodPressureMasterList";
        public static final String GET_MEDICAL_LIST = SERVICE_NAMESPACE + READ + "GetMedicalList";
        public static final String GET_FUTURE_CAMPLIST = SERVICE_NAMESPACE + READ + "GetFutureCampdetail";

        public static final String GET_CHARTNORMAL_RANGE = SERVICE_NAMESPACE + READ + "GetChartNormalRangeMasterList?";
        public static final String GET_CHARTCOMMONMASTER_RANGE = SERVICE_NAMESPACE + READ + "GetChartCommonMasterList";

        public static final String SEND_CAMP_COMPLETE_STATUS = SERVICE_NAMESPACE + UPDATE + "AddCampCompleteDetails";

        // ********************************* Add **************************************** //

        public static final String ADD_PATIENT_REGISTRATION = SERVICE_NAMESPACE + UPDATE
                + "AddPatientRegistration";
//        public static final String UPLOAD_PATIENT_PROFILE = SERVICE_NAMESPACE + "UploadPatientProfile.ashx";
        public static final String UPLOAD_PATIENT_PROFILE = SERVICE_NAMESPACE + UPDATE + "UploadPatientProfile";
        public static final String ADD_PATIENT_MEDICAL_DETAILS = SERVICE_NAMESPACE + UPDATE
                + "AddPatientMedicalDetail";

        public static final String ADD_OUTREACH_SYNCH_DETAILS = SERVICE_NAMESPACE + UPDATE + "AddOutreachSyncDetails";

        //String url = "https://impactindiafoundation.org/ImpactWebService/rest/impactwebcall/AddOutreachDetails";

        public static final String UPLOAD_OUTREACH_DATA = SERVICE_NAMESPACE + UPDATE + "AddOutreachDetails";

    }

    public static class Constants {
        public static final boolean DEBUG = true;
        public static final int MINUS_ONE = -1;
        public static final int ZERO = 0;
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final String SYNC_MASTER = "sync_master";
        public static final String CAMP_COMPLETE = "camp_complete";
        public static final String PRESCRIPTION_DATE_FORMAT = "dd-MM-yyyy hh-mm-ss a";
        public static final String MODIFIED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        public static final String SHORT_DATE_FORMAT = "dd-MM-yyyy";
        public static final String REVERSE_SHORT_DATE = "yyyy-MM-dd";
        public static final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";
        public static final int DEFAULT_TIMEOUT = 20 * 1000;
    }


}
