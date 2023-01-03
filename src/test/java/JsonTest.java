import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {


    public static void main(String[] args){
        User user = new User();
        UserName userName = new UserName();
        userName.setFamilyName("king");
        userName.setSecondName("liang");
        userName.setLastName("shui");
        user.setFname("king");
        user.setSecondName("lang");
        user.setSecondName("liangshui");
        user.setUserName(userName);
        user.setStatus("0");
        user.setMsg("ok");

        String jsonUserStr = JSON.toJSONString(user);
        System.out.println(jsonUserStr);
        String jsonUserStr ="{\"F_Name\":\"king\",\"Second_Name\":[\"lang\",\"liangshui\"],\"userName3\":{\"familyName\":\"king\",\"lastName\":\"shui\",\"secondName\":\"liang\"}}";
        User user2 = JSON.parseObject(jsonUserStr,User.class);
        System.out.println(user2.getFname());
        System.out.println(user2.getSecondName());
    }

}
