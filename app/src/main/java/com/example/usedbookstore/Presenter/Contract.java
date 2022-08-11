package com.example.usedbookstore.Presenter;

// View와 Presenter를 연결하기 위한 상호작용 인터페이스
public interface Contract {

    interface View {
        void LoginResult(); // 로그인 버튼 액션에 대한 결과
    }

    interface Presenter {
        void LoginAction(); // 로그인 버튼 액션
    }
}