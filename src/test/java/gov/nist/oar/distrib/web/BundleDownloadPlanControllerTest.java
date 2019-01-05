/**
 * This software was developed at the National Institute of Standards and Technology by employees of
 * the Federal Government in the course of their official duties. Pursuant to title 17 Section 105
 * of the United States Code this software is not subject to copyright protection and is in the
 * public domain. This is an experimental system. NIST assumes no responsibility whatsoever for its
 * use by other parties, and makes no guarantees, expressed or implied, about its quality,
 * reliability, or any other characteristic. We would appreciate acknowledgement if the software is
 * used. This software can be redistributed and/or modified freely provided that any derivative
 * works bear some notice that they are derived from it, and any modified versions bear some notice
 * that they have been modified.
 * @author: Deoyani Nandrekar-Heinis
 */
package gov.nist.oar.distrib.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nist.oar.distrib.web.objects.BundleNameFilePathUrl;
import gov.nist.oar.distrib.web.objects.FilePathUrl;

/**
 * @author Deoyani Nandrekar-Heinis
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NISTDistribServiceConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "distrib.bagstore.mode=local",
        "distrib.bagstore.location=./src/test/resources",
        "distrib.baseurl=http://localhost/oar-distrb-service",
        "logging.path=./target/surefire-reports",
        "distrib.filesizelimit = 2000000",
      "distrib.numberoffiles = 2",
      "distrib.validdomains = s3.amazonaws.com/nist-midas, nist-midas,www.uab.edu "
})
public class BundleDownloadPlanControllerTest {
    Logger logger = LoggerFactory.getLogger(DataBundleAccessControllerTest.class);

    @LocalServerPort
    int port;

    TestRestTemplate websvc = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    private String getBaseURL() {
	return "http://localhost:" + port + "/oar-dist-service";
    }
    
    @Test
    public void testDownloadAllFiles()
	    throws JsonParseException, JsonMappingException, IOException, URISyntaxException, Exception {
	FilePathUrl[] inputfileList = new FilePathUrl[2];
	String val1 = "{\"filePath\":\"/1894/license.pdf\",\"downloadUrl\":\"https://s3.amazonaws.com/nist-midas/1894/license.pdf\"}";
	String val2 = "{\"filePath\":\"/1894/license2.pdf\",\"downloadUrl\":\"https://www.uab.edu/students/academics/images/academic-calendar/2018-2019.pdf\"}";

	ObjectMapper mapper = new ObjectMapper();
	FilePathUrl testval1 = mapper.readValue(val1, FilePathUrl.class);
	FilePathUrl testval2 = mapper.readValue(val2, FilePathUrl.class);
	inputfileList[0] = testval1;
	inputfileList[1] = testval2;
	BundleNameFilePathUrl bFL = new BundleNameFilePathUrl("testdownload", inputfileList);
	RequestEntity<BundleNameFilePathUrl> request = RequestEntity.post(new URI(getBaseURL() + "/ds/_bundle_plan"))
		.body(bFL);

	ResponseEntity<String> response = websvc.exchange(request, String.class);
	 System.out.println("response.getStatusCode():"+response.getStatusCode()
	 + " \n resp.getHeaders():"+response.getHeaders()+
	 "\n response.getBody()"+response.getBody());

	assertEquals(HttpStatus.OK, response.getStatusCode());
//	assertTrue(response.getHeaders().getFirst("Content-Type").startsWith("application/zip"));
//	assertEquals(59903, response.getBody().length());

    }

//    @Test
//    public void testDownloadAllFilesException()
//	    throws JsonParseException, JsonMappingException, IOException, URISyntaxException, Exception {
//	FilePathUrl[] inputfileList = new FilePathUrl[3];
//	String val1 = "{\"filePath\":\"/1894/license.pdf\",\"downloadUrl\":\"https://s3.amazonaws.com/nist-midas/1894/license.pdf\"}";
//	String val2 = "{\"filePath\":\"/1895/license2.pdf\",\"downloadUrl\":\"https://s3.amazonaws.com/nist-midas/1894/license.pdf\"}";
//	String val3 = "{\"filePath\":\"/1896/license3.pdf\",\"downloadUrl\":\"https://s3.amazonaws.com/nist-midas/1894/license.pdf\"}";
//
//	ObjectMapper mapper = new ObjectMapper();
//	FilePathUrl testval1 = mapper.readValue(val1, FilePathUrl.class);
//	FilePathUrl testval2 = mapper.readValue(val2, FilePathUrl.class);
//	FilePathUrl testval3 = mapper.readValue(val3, FilePathUrl.class);
//	inputfileList[0] = testval1;
//	inputfileList[1] = testval2;
//	inputfileList[2] = testval3;
//
//	BundleNameFilePathUrl bFL = new BundleNameFilePathUrl("testdownload", inputfileList);
//	RequestEntity<BundleNameFilePathUrl> request = RequestEntity.post(new URI(getBaseURL() + "/ds/_bundle"))
//		.body(bFL);
//
//	ResponseEntity<String> response = websvc.exchange(request, String.class);
//	// System.out.println("response.getStatusCode()
//	// :"+response.getStatusCode()+
//	// " \n resp.getHeaders() :"+response.getHeaders()+
//	// "\n resp.getBody().length():"+response.getBody().length());
//
//	assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//	assertTrue(response.getHeaders().getFirst("Content-Type").startsWith("application/json"));
//	assertEquals(143, response.getBody().length());
//
//    }
}
