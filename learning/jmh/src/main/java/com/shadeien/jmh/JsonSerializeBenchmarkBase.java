package com.shadeien.jmh;

import com.shadeien.jmh.model.FullName;
import com.shadeien.jmh.model.Person;
import com.shadeien.jmh.utils.FastJsonUtil;
import com.shadeien.jmh.utils.GsonUtil;
import com.shadeien.jmh.utils.JacksonUtil;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class JsonSerializeBenchmarkBase {

    protected Person p;


    @Benchmark
    public void Gson() {
        GsonUtil.bean2Json(p);
    }

    @Benchmark
    public void FastJson() {
        FastJsonUtil.bean2Json(p);
    }

    @Benchmark
    public void Jackson() {
        JacksonUtil.bean2Json(p);
    }

    @Setup
    public void prepare() {
        List<Person> friends=new ArrayList<Person>();
        friends.add(createAPerson("小明",null));
        friends.add(createAPerson("Tony",null));
        friends.add(createAPerson("陈小二",null));
        p=createAPerson("邵同学",friends);
    }

    @TearDown
    public void shutdown() {
    }

    private Person createAPerson(String name,List<Person> friends) {
        Person newPerson=new Person();
        newPerson.setName(name);
        newPerson.setFullName(new FullName("zjj_first", "zjj_middle", "zjj_last"));
        newPerson.setAge(24);
        List<String> hobbies=new ArrayList<String>();
        hobbies.add("篮球");
        hobbies.add("游泳");
        hobbies.add("coding");
        newPerson.setHobbies(hobbies);
        Map<String,String> clothes=new HashMap<String, String>();
        clothes.put("coat", "Nike");
        clothes.put("trousers", "adidas");
        clothes.put("shoes", "安踏");
        newPerson.setClothes(clothes);
        newPerson.setFriends(friends);
        return newPerson;
    }
}
