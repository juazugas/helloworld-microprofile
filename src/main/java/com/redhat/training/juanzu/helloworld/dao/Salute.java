package com.redhat.training.juanzu.helloworld.dao;

public class Salute {

    private Integer id;

    private String log;

    /**
     * @return the id
     */
    public Integer getId () {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId (Integer id) {
        this.id = id;
    }

    /**
     * @return the log
     */
    public String getLog () {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog (String log) {
        this.log = log;
    }

    public static Builder builder () {
        return new Builder();
    }

    public static class Builder {

        private Salute salute = new Salute();

        public Builder id (Integer id) {
            salute.setId(id);
            return this;
        }

        public Builder log (String log) {
            salute.setLog(log);
            return this;
        }

        public Salute build () {
            return salute;
        }
    }
}
