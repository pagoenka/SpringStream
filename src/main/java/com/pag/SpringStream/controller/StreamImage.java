package com.pag.SpringStream.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
public class StreamImage {
	
	@RequestMapping("/streamImage")
	 public StreamingResponseBody handleRequest (HttpServletResponse response) {
		
		//Important to set Content type header
		response.setContentType("image/jpeg");
		
	        return new StreamingResponseBody() {
	            @Override
	            public void writeTo (OutputStream out) throws IOException {
	            	File fi = new File("/Users/Pratik/Desktop/me.jpg");
	            	try {
	        			byte[] fileContent = Files.readAllBytes(fi.toPath());
	        			int offset = 0,
	        				chunkLength = 10000,
	        				fileContentLength = fileContent.length;
	        			
	        			while (offset+ chunkLength < fileContentLength) {
		                    out.write(fileContent,offset, chunkLength );
		                    out.flush();
		                    offset = offset + chunkLength;
		                    
		                    if(fileContentLength < offset + chunkLength){
		                    		chunkLength = fileContentLength - offset;
		                    }
		                    //Deliberately adding sleep to emulate buffering image
		                    try {
		                        Thread.sleep(500);
		                    } catch (InterruptedException e) {
		                        e.printStackTrace();
		                    }
		                }
	        			out.close();
	        			
	        		} catch (IOException e) {
	        			System.out.println(e);
	        		}
	            }
	        };
	    }
	
}
