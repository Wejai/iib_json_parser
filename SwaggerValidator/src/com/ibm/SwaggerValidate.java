package com.ibm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import com.github.bjansen.ssv.SwaggerValidator;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.common.collect.ImmutableList;

public class SwaggerValidate {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String result = validateJSONPayload("E:\\GIT\\swagger.json", "E:\\GIT\\payload.json", "/definitions/User");
        System.out.println(result);

	}
	
	private static String readLineByLine(String filePath)
	{
		StringBuilder contentBuilder = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8))
		{
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return contentBuilder.toString();
	}
	
	public static String validateJSONPayload(String swaggerFile, String payload, String objectDefinition) {
		String output = "";
		try {
			File swagger = new File(swaggerFile);
			Reader spec = new FileReader(swagger);
			SwaggerValidator validator = SwaggerValidator.forJsonSchema(spec);
			String payloads = readLineByLine(payload);
			ProcessingReport report = validator.validate(payloads, "/definitions/User");
	
			if (report.isSuccess()) {
			    output = "Success";
			} else {
			    ImmutableList<ProcessingMessage> messages = ImmutableList.copyOf(report);
			    output = "Error : " + messages.get(0).getMessage();
			}
		} catch (Exception e) {
			output = "Error : " + e.getMessage();
		}
		return output;
	}

}
