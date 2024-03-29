package cn.itcast.graphql.vo;

/**
 * @author Young
 * @date 2019/12/17 14:06
 * @description：
 */
public class User {
    private Long id;
    private String name;
    private Integer age;
    private Card card;

    public User() {
    }

    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public User(Long id, String name, Integer age,Card card) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
