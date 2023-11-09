package com.luoying.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoying.model.entity.Question;
import com.luoying.service.QuestionService;
import com.luoying.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 落樱的悔恨
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2023-11-09 16:32:34
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




