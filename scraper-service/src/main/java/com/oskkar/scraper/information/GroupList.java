package com.oskkar.scraper.information;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupList {
    @Setter
    private String className;
    private List<ClassInformation> classInformation;

    public void addToClassInformation(ClassInformation classInformation) {
        this.classInformation.add(classInformation);
    }

}
