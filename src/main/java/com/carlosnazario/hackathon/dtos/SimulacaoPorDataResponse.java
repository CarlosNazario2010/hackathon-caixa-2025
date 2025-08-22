package com.carlosnazario.hackathon.dtos;

import java.util.List;

public class SimulacaoPorDataResponse {

    private List<ProdutoAgregadoResponse> listaSac;
    private List<ProdutoAgregadoResponse> listaPrice;

    // Construtor, Getters e Setters
    public SimulacaoPorDataResponse(List<ProdutoAgregadoResponse> listaSac, List<ProdutoAgregadoResponse> listaPrice) {
        this.listaSac = listaSac;
        this.listaPrice = listaPrice;
    }

    public List<ProdutoAgregadoResponse> getListaSac() {
        return listaSac;
    }

    public void setListaSac(List<ProdutoAgregadoResponse> listaSac) {
        this.listaSac = listaSac;
    }

    public List<ProdutoAgregadoResponse> getListaPrice() {
        return listaPrice;
    }

    public void setListaPrice(List<ProdutoAgregadoResponse> listaPrice) {
        this.listaPrice = listaPrice;
    }
}
