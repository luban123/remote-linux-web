package com.puwell.searchfind.searchfind.controller;

import com.puwell.searchfind.searchfind.bean.ConnectInfo;
import com.puwell.searchfind.searchfind.enu.ResultMsg;
import com.puwell.searchfind.searchfind.servcie.FindFileService;
import com.puwell.searchfind.searchfind.servcie.OpenFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/findFile")
public class FindFileController {

    @Autowired
    FindFileService findFileService;
    @Autowired
    OpenFileService openFileService;
    @RequestMapping(value = "/byPath",method = RequestMethod.POST)
    public Map<String,Integer> findFileByPath(@RequestBody ConnectInfo connectInfo,@RequestParam(value = "path",defaultValue = "/") String path){
        try {
            Map<String,Integer> map = findFileService.findFileByPath(connectInfo.getIp(), connectInfo.getName(), connectInfo.getPwd(), path,connectInfo.getPort());
            map.put("msg", ResultMsg.success.getCode());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Integer> map = new HashMap<>();
            map.put("msg",ResultMsg.error.getCode());
            return map;
        }
    }
    //打开文件
    @RequestMapping(value = "/openFile",method = RequestMethod.POST)
    public Map<String,Integer> openFile(@RequestBody ConnectInfo connectInfo,@RequestParam(value = "path")String path){
        System.out.println("我进来了。。。。。。。。。");
        Map<String ,Integer> map=new HashMap<>();
        try {
            String fileContent = openFileService.getFileContent(connectInfo, path);
            map.put(fileContent,ResultMsg.success.getCode());
            map.put("msg", ResultMsg.success.getCode());
            return map;
        } catch (Exception e) {
            map.put("msg",ResultMsg.error.getCode());
            return map;
        }
    }
}
