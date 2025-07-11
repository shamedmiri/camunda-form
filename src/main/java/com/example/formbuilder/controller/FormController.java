package com.example.formbuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class FormController {

    @GetMapping("/")
    public String showFormBuilder() {
        return "form-builder";
    }

    @PostMapping("/generate-form")
    public String generateForm(
            @RequestParam List<String> variableNames,
            @RequestParam List<String> variableTypes,
            Model model
    ) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<form>\n");

        for (int i = 0; i < variableNames.size(); i++) {
            html.append("<input type=\"text\" cam-variable-name=\"")
                    .append(variableNames.get(i))
                    .append("\" cam-variable-type=\"")
                    .append(variableTypes.get(i))
                    .append("\"><br/>\n");
        }

        html.append("<button type=\"submit\">ارسال</button>\n</form>");

        // ساخت فولدر اگر وجود نداشته باشد
        File folder = new File("src/main/resources/static/forms");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File output = new File(folder, "generated-form.html");
        try (FileWriter writer = new FileWriter(output)) {
            writer.write(html.toString());
        }

        // ریدایرکت به فرم ساخته شده
        return "redirect:/forms/generated-form.html";
    }
}
