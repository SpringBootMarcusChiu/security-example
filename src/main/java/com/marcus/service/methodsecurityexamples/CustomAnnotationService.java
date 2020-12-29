package com.marcus.service.methodsecurityexamples;

import com.marcus.annotation.IsViewer;
import org.springframework.stereotype.Service;

@Service
public class CustomAnnotationService {

    /**
     * see: annotation/IsViewer.java
     * @return
     */
    @IsViewer
    public String getUsername4() {
        return "marcus-chiu";
    }
}
