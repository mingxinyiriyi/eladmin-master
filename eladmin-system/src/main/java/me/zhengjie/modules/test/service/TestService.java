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
package me.zhengjie.modules.test.service;

import me.zhengjie.modules.test.domain.Test;
import me.zhengjie.modules.test.service.dto.TestDto;
import me.zhengjie.modules.test.service.dto.TestQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author ming
* @date 2021-10-08
**/
public interface TestService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(TestQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<TestDto>
    */
    List<TestDto> queryAll(TestQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return TestDto
     */
    TestDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return TestDto
    */
    TestDto create(Test resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Test resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<TestDto> all, HttpServletResponse response) throws IOException;
}