package com.xkx.chick.web.controller;

import cn.hutool.http.HttpUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.constant.OSSClientConstants;
import com.xkx.chick.common.util.AliyunOSSClientUtil;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.mapper.FileMapper;
import com.xkx.chick.sys.mapper.ZdxMapper;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.sys.pojo.entity.Zdx;
import com.xkx.chick.web.mapper.FilmContentMapper;
import com.xkx.chick.web.mapper.FilmMapper;
import com.xkx.chick.web.pojo.entity.Film;
import com.xkx.chick.web.pojo.entity.FilmContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @ClassName OpenController
 * @Author xiaokexin
 * @Date 2021/4/17 15:24
 * @Description 采集https://www.91mjw.cc/的美剧
 * @Version 1.0
 */
@RestController
public class OpenController {

    @Resource
    private AliyunOSSClientUtil aliyunOSSClientUtil;
    @Resource
    private ZdxMapper zdxMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private FilmMapper filmMapper;
    @Resource
    private FilmContentMapper filmContentMapper;


    @GetMapping("/updateFilmInfo")
    public void catchFilmInfo() {
        int index = 1;
        //前缀
        String pre = "https://www.91mjw.cc";
        //获取所有类型
        List<Zdx> zdxs = zdxMapper.selectByZdId("ccfb98c8-0299-48cd-adf6-43d9a3255513");
        //最后一页
        int lastPage;
        Document documentPage = Jsoup.parse(pre + "/meiju/1.html");
        String lastPageString = documentPage.select(".next-page + li").toString();
        lastPage = Integer.parseInt(lastPageString.split("-")[1].split("\\.")[0]);
        for (int i = 1; i <= lastPage; i++) {
            String page;
            if (i == 1) {
                page = "";
            } else {
                page = "-" + i;
            }
            /*处理美剧结束*/
            String s = HttpUtil.get("https://www.91mjw.cc/meiju/1" + page + ".html");
            Document document = Jsoup.parse(s);
            Elements elements = document.select(".m-movies .u-movie > a");
            for (Element element : elements) {
                System.out.println("===========================开始处理第" + index + "个美剧==========================");
                String href = element.attr("href");
                /*处理美剧开始*/
                String s2 = HttpUtil.get(pre + href);
                Document document2 = Jsoup.parse(s2);
                //创建一个美剧
                Film film = new Film();
                //处理封面,标题,信息,评分,简介
                film = titleAndInfo(film, document2, pre, zdxs);
                //处理美剧内容
                List<FilmContent> filmContents = filmContent(film, document2, pre);

                //插入数据库
                insertDB(film, filmContents);
                System.out.println("===========================处理完成第" + index + "个美剧==========================");
                System.out.println();
                System.out.println();
                index++;
            }
        }
    }

    @GetMapping("/updateOne")
    public R catchFilmInfo(String url) {
        //前缀
        String pre = "https://www.91mjw.cc";
        //获取所有类型
        List<Zdx> zdxs = zdxMapper.selectByZdId("ccfb98c8-0299-48cd-adf6-43d9a3255513");
        /*处理美剧开始*/
        String s2 = HttpUtil.get(url);
        Document document2 = Jsoup.parse(s2);
        //创建一个美剧
        Film film = new Film();
        //处理封面,标题,信息,评分,简介
        film = titleAndInfo(film, document2, pre, zdxs);
        //处理美剧内容
        List<FilmContent> filmContents = filmContent(film, document2, pre);
        //插入数据库
        insertDB(film, filmContents);
        return R.ok();
    }

    //处理封面,标题,信息,评分,简介
    private Film titleAndInfo(Film film, Document document, String pre, List<Zdx> zdxs) {
        System.out.println("====================开始处理封面,标题,信息,评分,简介===================");

        //标题
        Elements titleResult = document.select(".info-main-title a");
        String title = titleResult.attr("title");
        film.setName(title);

        //处理图片并赋值
        Elements imgByClass = document.getElementsByClass("video-info-img");
        String imgPath = imgByClass.attr("style").split("\"")[1];
        String myImgPath = img(pre + imgPath, title);
        film.setCoverUrl(myImgPath);

        //信息
        Elements infoResult = document.select(".video_info li");
        film.setId(UUID.randomUUID().toString());
        for (Element element : infoResult) {
            if (ObjectUtils.isNotEmpty(element)) {
                //更新
                if (element.select("span").text().contains("更新:")) {
                    if (element.text().split(":").length > 1) {
                        film.setFilmUpdateTime(element.text().split(":")[1]);
                    }
                }
                //状态
                if (element.select("span").text().contains("状态:")) {
                    if (element.text().split(":").length > 1) {
                        film.setStatue(element.text().split(":")[1]);
                    }
                }
                //导演
                if (element.select("span").text().contains("导演:")) {
                    if (element.text().split(":").length > 1) {
                        film.setDirector(element.text().split(":")[1]);
                    }
                }
                //别名
                if (element.select("span").text().contains("别名:")) {
                    if (element.text().split(":").length > 1) {
                        film.setOtherName(element.text().split(":")[1]);
                    }
                }
                //主演
                if (element.select("span").text().contains("主演:")) {
                    if (element.text().split(":").length > 1) {
                        film.setProtagonist(element.text().split(":")[1]);
                    }
                }
                //地区
                if (element.select("span").text().contains("地区:")) {
                    if (element.text().split(":").length > 1) {
                        film.setCountry(element.text().split(":")[1]);
                    }
                }
                //语言
                if (element.select("span").text().contains("语言:")) {
                    if (element.text().split(":").length > 1) {
                        film.setLanguage(element.text().split(":")[1]);
                    }
                }
                //首播
                if (element.select("span").text().contains("首播:")) {
                    if (element.text().split(":").length > 1) {
                        film.setDebut(element.text().split(":")[1]);
                    }
                }
                //电视台
                if (element.select("span").text().contains("电视台:")) {
                    if (element.text().split(":").length > 1) {
                        film.setTelevision(element.text().split(":")[1]);
                    }
                }
                //时长
                if (element.select("span").text().contains("时长:")) {
                    if (element.text().split(":").length > 1) {
                        film.setSingleSetOfOften(element.text().split(":")[1]);
                    }
                }
                //集数
                if (element.select("span").text().contains("集数:")) {
                    if (element.text().split(":").length > 1) {
                        film.setCount(element.text().split(":")[1]);
                    }
                }
                //类型
                if (element.select("span").text().contains("类型:")) {
                    if (element.text().split(":").length > 1) {
                        //设置展示类型
                        film.setType(element.text().split(":")[1]);
                    }
                    //设置标签
                    StringJoiner joiner = new StringJoiner(",", "", "");
                    if (element.text().split(":").length > 1) {
                        String[] types = element.text().split(":")[1].split(" ");
                        if (types.length > 0 && ObjectUtils.isNotEmpty(types)) {
                            for (String type : types) {
                                for (Zdx zdx : zdxs) {
                                    if (type.contains(zdx.getValue())) {
                                        joiner.add(zdx.getId());
                                    }
                                }
                            }
                        }
                        film.setTag(joiner.toString());
                    }
                }
                //播放支持
                if (element.select("span").text().contains("播放支持:")) {
                    if (element.text().split(":").length > 1) {
                        film.setPlayerSupport(element.text().split(":")[1]);
                    }
                }
            }
        }
        //评分
        String score = document.select(".video-score strong").text();
        film.setScore(score);
        //简介
        Elements elements = document.select(".mg-abstract");
        for (Element element : elements) {
            if (element.select("strong").text().contains("剧情简介")) {
                film.setIntro(element.select(".abstract-content").text());
            }
        }
        System.out.println("====================处理完成封面,标题,信息,评分,简介===================");

        return film;
    }

    //处理图片
    private String img(String imgPath, String title) {
        System.out.println("=============================开始处理图片============================");
        String url;
        try {
            //初始化OSSClient
            OSSClient ossClient = aliyunOSSClientUtil.getOSSClient();
            InputStream inputStream = new URL(imgPath).openStream();
            //上传文件
            PutObjectResult putResult = ossClient.putObject("chickweb", "filmFiles/" + title + "/" + title + ".jpg", inputStream);
            //获取唯一key
            String md5key = putResult.getETag();
            ObjectMetadata img = ossClient.getObjectMetadata("chickweb", "filmFiles/" + title + "/" + title + ".jpg");
            long contentLength = img.getContentLength();
            //关闭OSSClient
            ossClient.shutdown();
            //获取地址
            url = OSSClientConstants.URL_HEAD + "filmFiles/" + title + "/" + title + ".jpg";
            //插入文件表 如果存在则更新地址
            SysFile sysFile = fileMapper.selectOne(Wrappers.<SysFile>lambdaQuery()
                    .eq(SysFile::getMd5key, md5key)
                    .eq(SysFile::getDelFlag, CommonConstants.DELETE_FLAG));
            if (ObjectUtils.isEmpty(sysFile)) {
                String uuid = UUID.randomUUID().toString();
                SysFile sysfile = new SysFile(uuid, md5key, title + ".jpg", "a073ab64-6237-4c0c-860a-74e391ff4c51", url, title + ".jpg", contentLength);
                if (fileMapper.insert(sysfile) > 0) {
                    return url;
                }
            }else {
                sysFile.setUrl(url);
                if (fileMapper.updateById(sysFile) > 0){
                    return url;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "bad";
        }
        System.out.println("=============================处理完成图片============================");

        return url;
    }

    //处理视频内容信息
    private List<FilmContent> filmContent(Film film, Document document, String pre) {
        System.out.println("=========================开始处理视频内容信息=========================");
        ArrayList<FilmContent> filmContents = new ArrayList<>();
        Elements elements = document.select("#video_list_li");
        for (Element element : elements) {
            Elements contents = element.select("a");
            for (Element content : contents) {
                FilmContent filmContent = new FilmContent();
                filmContent.setFilmId(film.getId());
                //线路类型
                for (int i = 1; i <= 20; i++) {
                    if (element.select(".title strong").text().contains(i + "")) {
                        filmContent.setType(i + "");
                    } else if (element.select(".title strong").text().contains("哔哩哔哩")) {
                        filmContent.setType("foreShow");
                    }
                }
                //视频标题
                String title = content.attr("title");
                filmContent.setName(title);
                //视频地址
                String href = content.attr("href");
                String s = HttpUtil.get(pre + href);
                Document parse = Jsoup.parse(s);
                Element element1 = parse.select(".box script").get(0);
                String s1 = element1.toString();
                String split = s1.split("var now=\"")[1];
                String url = split.split("\";var pn")[0];
                filmContent.setId(UUID.randomUUID().toString());
                filmContent.setUrl(url);
                filmContents.add(filmContent);
            }

        }
        //下载地址
        Elements elementss = document.select(".mg-abstract");
        for (Element element : elementss) {
            if (element.select("strong").text().contains("迅雷下载")) {
                String string = element.select("div div div div ul script").get(1).toString();
                if (StringUtils.isNotEmpty(string)) {
                    if (string.contains("GvodUrls1")) {
                        String s = string.split("var GvodUrls1 = \"")[1];
                        String s1 = s.split("\";echoDown")[0];
                        String[] split = s1.split("###");
                        for (String s2 : split) {
                            String[] s3 = s2.split("\\$");
                            FilmContent filmContent = new FilmContent();
                            filmContent.setFilmId(film.getId());
                            filmContent.setId(UUID.randomUUID().toString());
                            filmContent.setType("download");
                            filmContent.setName(s3[0]);
                            filmContent.setUrl(s3[1]);
                            filmContents.add(filmContent);
                        }
                    }
                    if (string.contains("GvodUrls2")) {
                        String s = string.split("var GvodUrls2 = \"")[1];
                        String s1 = s.split("\";echoDown")[0];
                        String[] split = s1.split("###");
                        for (String s2 : split) {
                            String[] s3 = s2.split("\\$");
                            FilmContent filmContent = new FilmContent();
                            filmContent.setFilmId(film.getId());
                            filmContent.setId(UUID.randomUUID().toString());
                            filmContent.setType("download");
                            filmContent.setName(s3[0]);
                            filmContent.setUrl(s3[1]);
                            filmContents.add(filmContent);
                        }
                    }
                }
            }
        }
        System.out.println("=========================处理完成视频内容信息=========================");
        return filmContents;
    }

    //插入数据库
    private void insertDB(Film film, List<FilmContent> filmContents) {
        System.out.println("=============================开始插入数据============================");
        Film filmQuery = filmMapper.selectOne(Wrappers.<Film>lambdaQuery()
                .eq(Film::getName, film.getName()));
        if (ObjectUtils.isNotEmpty(filmQuery)) {
            //存在时的操作 将查询到的主键赋值
            film.setId(filmQuery.getId());
            //更新
            filmMapper.updateById(film);
            for (FilmContent filmContent : filmContents) {
                FilmContent filmContentQuery = filmContentMapper.selectOne(Wrappers.<FilmContent>lambdaQuery()
                        .eq(FilmContent::getFilmId, film.getId())
                        .eq(FilmContent::getName, filmContent.getName())
                        .eq(FilmContent::getType, filmContent.getType())
                        .eq(FilmContent::getUrl, filmContent.getUrl()));
                if (ObjectUtils.isNotEmpty(filmContentQuery)) {
                    filmContent.setId(filmContentQuery.getId());
                    filmContent.setFilmId(filmQuery.getId());
                    filmContentMapper.updateById(filmContent);
                } else {
                    filmContent.setFilmId(filmQuery.getId());
                    filmContentMapper.insert(filmContent);
                }
            }
        } else {
            filmMapper.insert(film);
            for (FilmContent filmContent : filmContents) {
                filmContentMapper.insert(filmContent);
            }
        }
        System.out.println("=============================插入数据完成============================");
    }
}
