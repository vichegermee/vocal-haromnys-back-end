-- 7 new photos were added to the frontend's public/images/home/ folder
-- (home-5.jpg through home-11.jpg, cropped/resized to match the existing
-- home-1..4 3:2 convention) to join the homepage hero carousel.
insert into home_banners (image_url, display_order) values
    ('/images/home/home-5.jpg', 5),
    ('/images/home/home-6.jpg', 6),
    ('/images/home/home-7.jpg', 7),
    ('/images/home/home-8.jpg', 8),
    ('/images/home/home-9.jpg', 9),
    ('/images/home/home-10.jpg', 10),
    ('/images/home/home-11.jpg', 11);
