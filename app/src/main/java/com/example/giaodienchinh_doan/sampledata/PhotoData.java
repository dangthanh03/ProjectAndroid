package com.example.giaodienchinh_doan.sampledata;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PhotoData {
    public static ArrayList<Photo>generatePhoto(){
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo(0,"Discover the lastest","Air Force 1 của Nike luôn được biết đến như một biểu tượng vĩ đại trong thế giới giày thể thao. Tuy nhiên, điều đặc biệt về đôi giày Air Force 1 Custom là tính cá nhân hóa tối đa mà chúng mang lại. Đây không chỉ là một đôi giày, mà còn là tác phẩm nghệ thuật độc đáo thể hiện phong cách và cá tính riêng của bạn.\n" +
                "\n" +
                "Bằng cách tùy chỉnh đôi giày Air Force 1, bạn có thể thể hiện sự sáng tạo và phong cách cá nhân một cách tối đa. Bạn có thể lựa chọn từ một loạt màu sắc, chất liệu, và họa tiết để tạo ra một đôi giày hoàn toàn độc đáo. Thậm chí, bạn còn có thể thêm các chi tiết riêng biệt như tên, hình vẽ hoặc thông điệp cá nhân để làm cho đôi giày thêm phần đặc biệt.\n" +
                "\n" +
                "Đôi giày Air Force 1 Custom không chỉ giới hạn trong việc thể hiện phong cách thời trang, mà còn là một biểu tượng của sự tự do sáng tạo. Đây là cơ hội để bạn biến một đôi giày thể thao thông thường thành một tác phẩm nghệ thuật điển hình của cá tính riêng. Bất kể bạn là người yêu thời trang, hoặc bạn đang tìm kiếm cách thể hiện bản thân qua sự khác biệt, đôi giày Air Force 1 Custom sẽ là lựa chọn hoàn hảo để thể hiện phong cách và cá tính độc đáo của bạn.","https://product.hstatic.net/200000255805/product/z4119132442993_a6b529c6a7782275d271a9ee73b2d866_0b291e1aa0574624839c86a10d8ddaf4_master.jpg"));
        photos.add(new Photo(1,"New Arrivals","New Arrivals","https://c.static-nike.com/a/images/f_auto,cs_srgb/w_1536,c_limit/g1ljiszo4qhthfpluzbt/123-joyride-cdp-apla-xa-xp.jpg"));
        photos.add(new Photo(2,"New Shoes","Những đôi giày mới đang trở thành biểu tượng của sự tiến bộ và sáng tạo trong thế giới thời trang. Như một bức tranh trắng sẵn sàng để được tô điểm, đôi giày mới mang theo sự hứa hẹn của những trải nghiệm hoàn toàn mới mẻ và phong cách đầy độc đáo.","https://www.kershkicks.co.uk/cdn/shop/articles/snkrs_2_1800x_98c5f923-cdbc-4d56-a84b-844374236744.png?v=1682854570"));
        photos.add(new Photo(3,"Icon make fresh","\n" +
                "\"Icon Make Fresh\" là một biểu tượng của sự tươi mới và đổi mới. Trong một thế giới ngập tràn các biểu tượng thời trang, làm thế nào chúng ta có thể tạo nên sự khác biệt, sự độc đáo trong phong cách của mình? Icon Make Fresh không chỉ là một xu hướng, mà còn là một triết lý sống. Đó là sự thách thức bản thân để không ngừng khám phá, sáng tạo và làm mới mọi khía cạnh của cuộc sống.","https://images.ctfassets.net/gl3ev2s7u7gx/3Cc2gyRwCUrdYqYg0IVEWk/f8e2eb48047ccf6fc940200cd34ec901/Nike_Air_Max_Day-2.jpeg?w=2000"));
        return photos;
    }
    public static Photo getPhotoFromId(int id) {
        ArrayList<Photo> photos = generatePhoto();
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).getId() == id) {
                return photos.get(i);
            }
        }
        return null;
    }}
