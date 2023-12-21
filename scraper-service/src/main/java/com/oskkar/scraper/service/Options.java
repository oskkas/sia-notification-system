package com.oskkar.scraper.service;

public enum Options {
    SELECT_STUDY_TYPE {
        public String getOption() {
           return "Pregrado";
        }
    },
    SELECT_FACULTY {
        public String getFaculty() {
            return "2055 FACULTAD DE INGENIERÍA";
        }
    },
    SELECT_DEGREE {
       public String getDegree() {
           return "2983 INGENIERÍA ELÉCTRICA";
       }
    }
}
