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
 * 
 * @author:Harold Affo (Prometheus Computing, LLC)
 */
package gov.nist.oar.ds.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.nist.oar.ds.service.DownloadService;

/**
 * This is the download controller class responsible of handling the download restful http requests
 *
 */
@RestController
@RequestMapping("/od/ds")
public class DownloadController {

  Logger logger = LoggerFactory.getLogger(DownloadController.class);


  @Autowired
  private DownloadService downloadService;

  public DownloadService getDownloadService() {
    return downloadService;
  }

  public void setDownloadService(DownloadService downloadService) {
    this.downloadService = downloadService;
  }

  /**
   * Download a distribution file by its id
   * 
   * @param dsId
   * @param distId
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/{dsId}/dist/{distId}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> download(@PathVariable("dsId") String dsId,
      @PathVariable("distId") String distId) throws IOException {
    logger.info("Downloading distribution file with distId=" + distId + " dsId=" + dsId);
    return downloadService.downloadDistributionFile(dsId, distId);
  }


  /**
   * Return the list of bags of a data set
   * 
   * @param dsId
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/{dsId}/bags", method = RequestMethod.GET)
  public ResponseEntity<List<String>> listDataSetBags(@PathVariable("dsId") String dsId)
      throws IOException {
    return downloadService.findDataSetBags(dsId);
  }

  /**
   * Return the head bag key of a data set
   * 
   * @param dsId: id of the data set
   * @return the head bag key of a data set
   * @throws IOException
   */
  @RequestMapping(value = "/{dsId}/headBag", method = RequestMethod.GET)
  public ResponseEntity<String> headBag(@PathVariable("dsId") String dsId) throws IOException {
    return downloadService.findDataSetHeadBag(dsId);
  }


  /**
   * Cache a data set
   * 
   * @param dsId
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/{dsId}/cache", method = RequestMethod.POST)
  public ResponseEntity<byte[]> cacheDataSet(@PathVariable("dsId") String dsId) throws IOException {
    return null;
  }


}
