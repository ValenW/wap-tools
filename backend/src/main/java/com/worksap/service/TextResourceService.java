package com.worksap.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.model.entity.TextResource;
import com.worksap.repository.TextResourceRepository;

@Service
public class TextResourceService {

    @Resource
    private TextResourceRepository textResourceRepository;

    public Iterable<TextResource> all() {
        return textResourceRepository.findAll();
    }

    public void add(List<TextResource> list) {
        list.forEach(this::add);
    }

    @Transactional
    public void delete(String id) {
        textResourceRepository.delete(id);
    }

    @Transactional
    public void delete(List<String> ids) {
        ids.forEach(this::delete);
    }

    public void add(TextResource tag) {
        TextResource old = textResourceRepository.findOne(tag.getId());
        if (old != null) {
            TextResource newText = TextResource.builder()
                    .id(old.getId())
                    .en(StringUtils.isNotBlank(tag.getEn()) ? tag.getEn() : old.getEn())
                    .ja(StringUtils.isNotBlank(tag.getJa()) ? tag.getJa() : old.getJa())
                    .createTime(old.getCreateTime())
                    .updateTime(LocalDateTime.now())
                    .build();
            textResourceRepository.save(newText);
        } else {
            tag.setCreateTime(LocalDateTime.now());
            tag.setUpdateTime(LocalDateTime.now());
            textResourceRepository.save(tag);
        }
    }
}
