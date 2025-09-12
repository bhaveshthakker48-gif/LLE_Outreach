package org.impactindia.llemeddocket.listener;

import org.impactindia.llemeddocket.pojo.KeyPairBoolData;

import java.util.List;

public interface SpinnerListener {
    void onItemsSelected(List<KeyPairBoolData> items);
}
