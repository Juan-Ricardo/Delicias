package com.pe.delicias.home;

import com.pe.delicias.apirest.response.plate.PlateResponse;

public interface PlateByCategoryListener {
    void onError(String error);
    void onSuccess(PlateResponse plateResponse);
}
