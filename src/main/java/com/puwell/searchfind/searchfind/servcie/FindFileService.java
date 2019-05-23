package com.puwell.searchfind.searchfind.servcie;

import ch.ethz.ssh2.Connection;
import com.puwell.searchfind.searchfind.utils.ConnnectRemoteComputerUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FindFileService {
    //判断是否是正整数
    private final String reg="^(0|[1-9]\\d*)\\b";
    public Map findFileByPath(String ip,String name,String pwd,String path,String port) throws Exception {
        if("".equals(port)||port==null||!reg.matches(reg)){
            port="22";
        }
        Connection connection = ConnnectRemoteComputerUtil.connectRemoteLinux(ip, name, pwd,Integer.parseInt(port));
        return ConnnectRemoteComputerUtil.getproperties(connection, path);
    }
}
