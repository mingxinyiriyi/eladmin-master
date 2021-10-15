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
package me.zhengjie.modules.test.service.impl;

import me.zhengjie.modules.test.domain.Test;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.test.repository.TestRepository;
import me.zhengjie.modules.test.service.TestService;
import me.zhengjie.modules.test.service.dto.TestDto;
import me.zhengjie.modules.test.service.dto.TestQueryCriteria;
import me.zhengjie.modules.test.service.mapstruct.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author ming
* @date 2021-10-08
**/
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public Map<String,Object> queryAll(TestQueryCriteria criteria, Pageable pageable){
        Page<Test> page = testRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(testMapper::toDto));
    }

    @Override
    public List<TestDto> queryAll(TestQueryCriteria criteria){
        return testMapper.toDto(testRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TestDto findById(Integer id) {
        Test test = testRepository.findById(id).orElseGet(Test::new);
        ValidationUtil.isNull(test.getId(),"Test","id",id);
        return testMapper.toDto(test);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestDto create(Test resources) {
        return testMapper.toDto(testRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Test resources) {
        Test test = testRepository.findById(resources.getId()).orElseGet(Test::new);
        ValidationUtil.isNull( test.getId(),"Test","id",resources.getId());
        test.copy(resources);
        testRepository.save(test);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            testRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TestDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TestDto test : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名字", test.getName());
            map.put("汽车", test.getCar());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}