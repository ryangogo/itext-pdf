package com.ryan.itextpdf.controller;


import com.ryan.itextpdf.utils.ItextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * describe：
 * Created with IDEA
 * author:ryan
 * Date:2019/8/7
 * Time:上午9:53
 */
@Controller
@RequestMapping("/")
public class HomeController {


    @GetMapping("/")
    @ResponseBody
    public void toHome(HttpServletResponse response) throws Exception {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/测试模板.pdf");
        String absolutePath = file.getAbsolutePath();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("name", "张三");
        paramMap.put("age", "18");
        paramMap.put("native place", "吉林省长春市延边朝鲜族自治州");
        paramMap.put("long text", "这是一个长文本测试，文本要很长很长很长才行；这是一个长文本测试，文本要很长很长很长才行；这是一个长文本测试，文本要很长很长很长才行" +
                "这是一个长文本测试，文本要很长很长很长才行；这是一个长文本测试，文本要很长很长很长才行");
        ItextUtil.generatePdfStream(absolutePath, "STSong-Light", paramMap, response);

    }


}
