package com.puwell.searchfind.searchfind.servcie;

import ch.ethz.ssh2.Connection;
import com.puwell.searchfind.searchfind.bean.ConnectInfo;
import com.puwell.searchfind.searchfind.utils.ConnnectRemoteComputerUtil;
import org.springframework.stereotype.Service;

@Service
public class OpenFileService {
    //判断是否是正整数
    private final String reg="^(0|[1-9]\\d*)\\b";

    /**
     * h获取文件内容信息
     * @param connectInfo
     * @param path
     * @return
     * @throws Exception
     */
    public String getFileContent(ConnectInfo connectInfo, String path) throws Exception {

        String cmd="cat "+path;
        String port=connectInfo.getPort();
        if("".equals(port)||port==null||!reg.matches(reg)){
            port="22";
        }
        Connection connection = ConnnectRemoteComputerUtil.connectRemoteLinux(connectInfo.getIp(), connectInfo.getName(), connectInfo.getPwd(), Integer.parseInt(port));
        return ConnnectRemoteComputerUtil.execute(connection,cmd);
    }
}
