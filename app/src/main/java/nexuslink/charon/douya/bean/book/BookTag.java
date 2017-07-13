package nexuslink.charon.douya.bean.book;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Charon on 2017/7/12.
 */

public class BookTag {
    public final static String[] BOOK_TAG = {"小说", "外国文学", "文学", "随笔", "中国文学", "经典", "日本文学", "散文", "村上春树", "童话", "诗歌",
            "杂文", "王小波", "儿童文学", "古典文学", "张爱玲", "余华", "名著", "当代文学", "钱钟书", "鲁迅", "外国名著", "诗词",
            "茨威格", "米兰·昆德拉", "杜拉斯", "港台", "漫画", "绘本", "推理", "青春", "言情", "科幻", "东野圭吾", "悬疑", "武侠",
            "奇幻", "韩寒", "日本漫画", "耽美", "亦舒", "三毛", "安妮宝贝", "网络小说", "推理小说", "郭敬明", "穿越", "金庸", "轻小说",
            "阿加莎·克里斯蒂", "几米", "张小娴", "魔幻", "幾米", "青春文学", "科幻小说", "J.K.罗琳", "高木直子", "古龙", "沧月", "落落",
            "张悦然", "蔡康永", "历史", "心理学", "哲学", "传记", "文化", "社会学", "艺术", "设计", "政治", "社会", "建筑", "宗教", "电影",
            "数学", "政治学", "回忆录", "思想", "中国历史", "国学", "音乐", "人文", "戏剧", "人物传记", "绘画", "艺术史", "佛教", "军事",
            "西方哲学", "近代史", "二战", "考古", "自由主义", "美术", "爱情", "旅行", "生活", "成长", "励志", "心理", "摄影", "女性", "职场",
            "美食", "教育", "游记", "灵修", "健康", "情感", "手工", "养生", "两性", "人际关系", "家居", "自助游", "经济学", "管理", "经济",
            "金融", "商业", "投资", "营销", "创业", "理财", "广告", "股票", "企业史", "策划", "科普", "互联网", "编程", "科学", "交互设计",
            "用户体验", "算法", "web", "科技", "UE", "通信", "交互", "UCD", "神经网络", "程序"};

    public static List<String> getBookTagList() {
        return Arrays.asList(BOOK_TAG);
    }


}