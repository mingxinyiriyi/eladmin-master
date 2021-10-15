/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.test.rest;

import cn.hutool.http.HttpUtil;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.test.domain.Test;
import me.zhengjie.modules.test.service.TestService;
import me.zhengjie.modules.test.service.dto.TestQueryCriteria;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author ming
* @date 2021-10-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "测试生产管理")
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('test:list')")
    public void download(HttpServletResponse response, TestQueryCriteria criteria) throws IOException {
        testService.download(testService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询测试生产")
    @ApiOperation("查询测试生产")
    @PreAuthorize("@el.check('test:list')")
    public ResponseEntity<Object> query(TestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testService.queryAll(criteria,pageable),HttpStatus.OK);
//        HttpUtil.get()
    }

    @PostMapping
    @Log("新增测试生产")
    @ApiOperation("新增测试生产")
    @PreAuthorize("@el.check('test:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Test resources){
        return new ResponseEntity<>(testService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改测试生产")
    @ApiOperation("修改测试生产")
    @PreAuthorize("@el.check('test:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Test resources){
        testService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除测试生产")
    @ApiOperation("删除测试生产")
    @PreAuthorize("@el.check('test:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        testService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Log("请求https服务")
    @ApiOperation("请求https服务")
    @PreAuthorize("@el.check('test:getBscData')")
    @RequestMapping("getBscData")
    public ResponseEntity<Object> getBscData() {
//        String htmlStr =HttpUtil.get("https://bscscan.com/tokenholdings?a=0xa268fbe8e0e385bc97b2fb91a4fc8e346f1c92d6&ps=100&sort=total_price_usd&order=desc&p=1");
//        System.out.println(htmlStr);
        try {
            Parser parser = new Parser((HttpURLConnection) (new URL("https://bscscan.com/tokenholdings?a=0xa268fbe8e0e385bc97b2fb91a4fc8e346f1c92d6&ps=100&sort=total_price_usd&order=desc&p=1")).openConnection());
            System.out.println(parser.toString());
           NodeList nodes =  parser.parse(null);
           String html = nodes.toHtml();
            for (int i = 0; i < nodes.size(); i++) {
               Node node =  nodes.elementAt(i);
            }
            System.out.println(html);
            System.out.println("哈哈哈");
        } catch (ParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}