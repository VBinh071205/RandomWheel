## RandomWheel Project ##

RandomWheel là một ứng dụng Java giúp tạo ra một vòng quay ngẫu nhiên để lựa chọn ngẫu nhiên từ danh sách các mục nhập.
Ứng dụng có thể được sử dụng cho nhiều mục đích như chọn người chiến thắng trong một cuộc thi, quyết định ngẫu nhiên một lựa chọn, hoặc tạo sự ngẫu nhiên cho các trò chơi.

## Tính năng / Features

- Quay vòng ngẫu nhiên: Bạn có thể tạo vòng quay với danh sách các mục tùy chỉnh.
  
- Chỉnh sửa mục nhập: Thêm, sửa, hoặc xóa các mục trong vòng quay.
  
- Hiển thị kết quả: Hiển thị mục thắng cuộc sau khi vòng quay kết thúc.

## Yêu cầu / Prerequisites: JDK (Phiên bản 8 trở lên)

## Cài đặt ứng dụng / Installing the app

1. Clone repository về máy: git clone https://github.com/VBinh071205/RandomWheel.git

2. Biên dịch file java : javac RandomWheel.java

3. Chạy chương trình: java RandomWheel.java

## Lưu ý / Attentive

1. Project vẫn chưa có UI, đang cần fix thêm

2. Đang tồn tại lỗi lựa chọn không đồng bộ với mảng màu, mảng màu chia theo hình quạt nhưng lựa chọn bị chia theo chiều dọc

3. Vẫn chưa có tính năng sửa các mục trong vòng quay, đang học tập thêm

4. Sẽ không có tính năng tuỳ chọn màu sắc (hoặc tương lai sẽ có nếu học tập đủ tốt)

## Thông tin dự án / Project information
                      ### Null ###
## Cách sử dụng git / How to use git (áp dụng với VSCode)

1. Cài đặt Git
Trước hết, bạn cần cài đặt Git nếu chưa cài. Bạn có thể tải và cài đặt Git từ [trang chính thức của Git](https://git-scm.com/).

2. Cấu hình Git
Mở terminal trong Visual Studio Code và cấu hình tên và email cho Git (nếu chưa làm):
- git config --global user.name "Tên của bạn"
- git config --global user.email "email@domain.com"

3. Khởi tạo Git repository
Trong thư mục dự án của bạn, mở terminal và chạy lệnh `git init` để khởi tạo một Git repository mới: 

4. Thêm tệp vào Git
Thêm tất cả các tệp trong dự án vào Git (hoặc chỉ những tệp bạn muốn đưa lên): `git add (.) (tên tệp muốn đưa lên)`

5. Commit mã
Tiến hành commit với một thông điệp miêu tả thay đổi: `git commit -m "Lời miêu tả thay đổi của bạn"`

6. Tạo repository trên GitHub
- Truy cập [GitHub](https://github.com/), đăng nhập và tạo một repository mới.
- Đặt tên cho repository và chọn các tùy chọn cần thiết.
- Sau khi tạo repository, GitHub sẽ cung cấp các lệnh để kết nối repository cục bộ với repository trên GitHub.

7. Kết nối repository cục bộ với GitHub
Trong terminal, kết nối repository cục bộ của bạn với repository trên GitHub:
- git remote add origin https://github.com/TênCủaBạn/TênRepository.git
- Thay "TênCủaBạn" và "TênRepository" bằng thông tin của bạn.

8. Đẩy mã lên GitHub
Cuối cùng, đẩy mã từ repository cục bộ lên GitHub:
- git push -u origin master
- Nếu bạn sử dụng nhánh khác, thay `master` bằng tên nhánh của bạn, chẳng hạn `main`.

9. Xác thực (nếu cần)
- GitHub có thể yêu cầu bạn nhập tên người dùng và mật khẩu, hoặc sử dụng token thay thế mật khẩu nếu bạn sử dụng xác thực 2 yếu tố.

- Sau khi hoàn tất, bạn có thể vào GitHub và kiểm tra mã của mình trên repository vừa tạo.

- Sau này khi muốn đẩy code chỉ cần làm `4`, `5`, và `8` là được 
  
10. Lưu ý : Dùng `git pull` trước khi ` git push` tránh trường hợp gây lỗi
