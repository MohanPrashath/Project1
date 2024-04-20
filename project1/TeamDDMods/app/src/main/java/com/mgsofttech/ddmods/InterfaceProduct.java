package com.mgsofttech.ddmods;

import com.mgsofttech.ddmods.model.ModelServer;

public interface InterfaceProduct {
    void onFailed(String str);

    void onSuccess(ModelServer modelServer);
}