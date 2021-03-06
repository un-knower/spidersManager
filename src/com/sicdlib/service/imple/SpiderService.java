package com.sicdlib.service.imple;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.sicdlib.dao.IDataDictDAO;
import com.sicdlib.dao.ISpiderDAO;
import com.sicdlib.dto.SpiderInfoEntity;
import com.sicdlib.service.ISCHSessionService;
import com.sicdlib.service.ISpiderService;
import com.sicdlib.util.JSCHUtil.ExecUtils;
import com.sicdlib.util.JSCHUtil.SFTPChannel;
import com.sicdlib.util.JSCHUtil.JSCHConstants;
import com.sicdlib.util.UUIDUtil.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoyang on 2017/4/24.
 */
@Configuration
@ImportResource("classpath:beans.xml")
@Service("spiderService")
public class SpiderService implements ISpiderService {
    @Autowired
    @Qualifier("spiderDAO")
    private ISpiderDAO spiderDAO;

    @Autowired
    @Qualifier("dataDictDAO")
    private IDataDictDAO dataDictDAO;

    @Autowired
    @Qualifier("schSessionService")
    private ISCHSessionService schSessionService;

    @Override
    public String saveSpiderInfo(HttpServletRequest req, SpiderInfoEntity spiderInfo) throws Exception {
        //使用sftp传输文件

        String fileName = spiderInfo.getFileName();
        String fileID = spiderInfo.getFileId();

        String src = dataDictDAO.getDictValue("SPIDER_SOURCE_TMP").get(0);
        String dest = dataDictDAO.getDictValue("SPIDER_SOURCE_DEST").get(0);
//        String logs_path = dataDictDAO.getDictValue("SPIDER_LOGS_PATH").get(0);
//        String files_path = dataDictDAO.getDictValue("SPIDER_FILES_PATH").get(0);



        src += fileID;
        String realSrc = req.getSession().getServletContext().getRealPath(src);
        String srcPath = dest + "spiderSource/";
//        String logPath = logs_path + fileName + "/";
//        String filePath = files_path + fileName + "/";
        String sftpDest = dest + "spiderZip/" + fileName + ".zip";

//        try {
//            String mkdirFilesCommand = "mkdir -p " + filePath;
//            String mkdirLogsCommand = "mkdir -p " + logPath;
//            String command = mkdirLogsCommand + "; " + mkdirFilesCommand;
//            String result = "";
//            try {
//                result = ExecUtils.getInstance().execCmd(execSession, command);// 多条命令之间以;分隔
//            } catch (Exception e) {
//                System.out.println(e + result);
//                return false;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            return false;
//        }

        Map<String, String> sftpDetails = schSessionService.getSftpDetail();
        int timeout = schSessionService.getTimeout();
        Session execSession = schSessionService.getSession();

        SFTPChannel sftpChannel = new SFTPChannel();
        ChannelSftp chSftp = (ChannelSftp) sftpChannel.getChannel(sftpDetails, timeout);

        try {
            chSftp.put(realSrc, sftpDest, ChannelSftp.OVERWRITE);
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }

        chSftp.quit();
        sftpChannel.closeChannel();

        //使用command解压文件
        String unzipCommand = "unzip " + sftpDest + " -d " + srcPath + fileName + "/";
        String result = "";
        try {
            result = ExecUtils.getInstance().execCmd(execSession, unzipCommand);// 多条命令之间以;分隔
        } catch (Exception e) {
            System.out.println(e + result);
            return "";
        }

        //创建configID文件
        String touchConfigCommand = "touch " + srcPath + fileName + "/" + "config";
        String touchResult = "";
        try {
            touchResult = ExecUtils.getInstance().execCmd(execSession, touchConfigCommand);// 多条命令之间以;分隔
            ExecUtils.getInstance().clear(execSession);
        } catch (Exception e) {
            System.out.println(e + touchResult);
            return "";
        }

        String uuid = UUIDUtil.getUUID();
        spiderInfo.setId(uuid);
        spiderInfo.setSpiderSourcePath(srcPath + fileName + "/");

        return spiderDAO.saveSpiderInfo(spiderInfo);
    }

    @Override
    public List<SpiderInfoEntity> getAllSpiderInfo() {
        return spiderDAO.getAllSpiderInfo();
    }

    @Override
    public List<Object[]> getAllSpiderInfoWebsite() {
        return spiderDAO.getAllSpiderInfoWebsite();
    }

    @Override
    public List<Object[]> getSpiderInfoWebsite(String spiderID) {
        return spiderDAO.getSpiderInfoWebsite(spiderID);
    }

    @Override
    public boolean updateSpiderInfo(String spiderID, String spiderName, String websiteID) {
        SpiderInfoEntity spiderInfo = spiderDAO.getSpiderInfo(spiderID);
        if(spiderInfo !=  null) {
            spiderInfo.setSpiderName(spiderName);
            spiderInfo.setWebsiteId(websiteID);

            spiderDAO.updateSpiderInfo(spiderInfo);

            return true;
        }

        return false;
    }
}
