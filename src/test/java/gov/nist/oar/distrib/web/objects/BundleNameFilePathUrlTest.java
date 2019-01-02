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
package gov.nist.oar.distrib.web.objects;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Deoyani Nandrekar-Heinis BundleNamefilePathUrl java object test.
 *
 */
public class BundleNameFilePathUrlTest {

    @Test
    public void testBundleNameFilePathUrl() {
	FilePathUrl fpathUrl_1 = new FilePathUrl("/filepath/file-1.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	FilePathUrl fpathUrl_2 = new FilePathUrl("/filepath/file-2.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	BundleNameFilePathUrl bundle1 = new BundleNameFilePathUrl("download_data_1",
		new FilePathUrl[] { fpathUrl_1, fpathUrl_2 });
	
	assertEquals("download_data_1", bundle1.getBundleName());
	assertEquals("/filepath/file-1.pdf", bundle1.getIncludeFiles()[0].getFilePath());
	assertEquals("https://s3.amazonaws.com/nist-midas/1894/license.pdf", bundle1.getIncludeFiles()[0].getDownloadUrl());
	
    }
    
    @Test
    public void testJson() throws JsonProcessingException, JSONException{
	String fileUrl1= "{\"filePath\":\"/filepath/file-1.pdf\",\"downloadUrl\":\"https://s3.amazonaws.com/nist-midas/1894/license.pdf\"}";
	String fileUrl2 = "{\"filePath\":\"/filepath/file-2.pdf\",\"downloadUrl\":\"https://s3.amazonaws.com/nist-midas/1894/license.pdf\"}";
	
	String bundleNAme = "\"bundleName\":\"download_data_1\" ";
	String bundleJson = "{"+bundleNAme+","+"\"includeFiles\""+":["+fileUrl1+","+fileUrl2+"]"+"}";
	
	FilePathUrl fpathUrl_1 = new FilePathUrl("/filepath/file-1.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	FilePathUrl fpathUrl_2 = new FilePathUrl("/filepath/file-2.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	BundleNameFilePathUrl bundle1 = new BundleNameFilePathUrl("download_data_1",
		new FilePathUrl[] { fpathUrl_1, fpathUrl_2 });
	
	String json = new ObjectMapper().writeValueAsString(bundle1);
	JSONAssert.assertEquals(bundleJson, json, true);
    }
    
    public BundleNameFilePathUrl[] makeBundles() {
	FilePathUrl fpathUrl_1 = new FilePathUrl("/filepath/file-1.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	FilePathUrl fpathUrl_2 = new FilePathUrl("/filepath/file-2.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	BundleNameFilePathUrl bundle1 = new BundleNameFilePathUrl("download_data_1",
		new FilePathUrl[] { fpathUrl_1, fpathUrl_2 });

	FilePathUrl fpathUrl_3 = new FilePathUrl("/filepath-2/testfile-1.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	FilePathUrl fpathUrl_4 = new FilePathUrl("/filepath-2/testfile-2.pdf",
		"https://s3.amazonaws.com/nist-midas/1894/license.pdf");
	BundleNameFilePathUrl bundle2 = new BundleNameFilePathUrl("download_data_2",
		new FilePathUrl[] { fpathUrl_3, fpathUrl_4 });

	return new BundleNameFilePathUrl[] { bundle1, bundle2 };
    }


}
