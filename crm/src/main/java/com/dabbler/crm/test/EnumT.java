package com.dabbler.crm.test;

interface info{
    void detail();
}

enum Season implements info {

    /**
     * 这里相当于类的构造方法，不过省略了公共的前缀(private final Season)
     */
    SPRING(1, "春天", "经常下雨") {
        @Override
        public void detail() {
            System.out.println("天街小雨润如酥，草色遥看近却无");
        }
    },
    SUMMER(2, "夏天", "很热") {
        @Override
        public void detail() {
            System.out.println("小荷才露尖尖角，早有蜻蜓立上头");
        }
    },
    FALL(3, "秋天", "很凉爽") {
        @Override
        public void detail() {
            System.out.println("空山新雨后，天气晚来秋");
        }
    },
    WINTER(4, "冬天", "很冷") {
        @Override
        public void detail() {
            System.out.println("忽如一夜春风来，千树万树梨花开");
        }
    };

    /**
     * 定义枚举对象的一些属性
     */
    private Integer id;
    private final String name;
    private final String feature;

    /**
     * 构造方法，注意是私有的
     *
     * @param name
     * @param feature
     */
    private Season(Integer id, String name, String feature) {
        this.id = id;
        this.name = name;
        this.feature = feature;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFeature() {
        return feature;
    }
}

public class EnumT {
    public static void main(String[] args) {
        for (Season s : Season.values()) {
            System.out.println(s);
        }

        Season spring = Season.valueOf("SPRING");
        System.out.println(spring.getFeature());
    }
}
