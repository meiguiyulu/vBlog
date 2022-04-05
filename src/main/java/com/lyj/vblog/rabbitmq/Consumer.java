package com.lyj.vblog.rabbitmq;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.lyj.vblog.common.Result;
import com.lyj.vblog.service.IArticleService;
import com.lyj.vblog.vo.ArticleMessage;
import com.lyj.vblog.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "work"))
public class Consumer {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RabbitHandler
    public void onMessage(ArticleMessage message) {
        log.info("收到的消息: {}", message);
        // 更新文章详情的缓存
/*        Long articleId = message.getArticleId();
        String params = SecureUtil.md5(articleId.toString());
        String redisKey = "view_articles::ArticleController::findArticleById::" + params;
        ArticleVo articleVo = articleService.findArticleById(articleId);
        Result articleResult = Result.success(articleVo);
        redisTemplate.opsForValue().set(redisKey, JSONUtil.toJsonStr(articleResult), Duration.ofMillis(5 * 60 * 1000));
        log.info("更新了缓存:{}", redisKey);*/
        //2. 文章列表的缓存 不知道参数,解决办法 直接删除缓存
        Set<String> keys = redisTemplate.keys("listArticle*");
        keys.forEach(s -> {
            redisTemplate.delete(s);
            log.info("删除了文章列表的缓存:{}", s);
        });
    }
}
