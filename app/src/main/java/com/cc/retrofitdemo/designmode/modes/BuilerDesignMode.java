package com.cc.retrofitdemo.designmode.modes;


public class BuilerDesignMode {
    private String url;
    private String data;

    private BuilerDesignMode(String url, String data) {
        this.url = url;
        this.data = data;
    }

    public String getString() {
        return url + ".." + data;
    }

    public static class Builder {
        private String myUrl;
        private String myData;

        public Builder buildUrl(String url) {
            this.myUrl = url;
            return this;
        }

        public Builder buildData(String data) {
            this.myData = data;
            return this;
        }

        public BuilerDesignMode build() {
            return new BuilerDesignMode(myUrl, myData);
        }

    }

}
