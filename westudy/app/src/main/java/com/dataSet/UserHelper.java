package com.dataSet;

/**
 * Created by KimYongSeong on 14. 11. 8..
 */
public class UserHelper {
    private static User _user;
    private static String name;

    public static void setUser(User logginedUser) {
        _user = new User();
        _user.set_id(logginedUser.get_id());
        _user.setEmail(logginedUser.getEmail());
        _user.setName(logginedUser.getName());
        _user.setProfile_url(logginedUser.getProfile_url());
        _user.setIntroduce((logginedUser.getIntroduce()));

        if (_user.getGender().equals("남") ) {
            _user.setGender("male");
        } else {
            _user.setGender("female");
        }

        int[] interest = logginedUser.getInterest();
        _user.initInterest(interest.length);
        for (int i = 0; i < interest.length; i++) {
            _user.setInterest(interest[i], i);
        }

    }

    public static User getUser() {
        return _user;
    }
}
