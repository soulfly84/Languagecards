package com.soulfly.englishcards.api;

import com.soulfly.englishcards.model.Card;
import com.soulfly.englishcards.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by Elena on 27.03.2017.
 */

public interface WebApi {
    //загружает все карточки
    @GET("/languageapp/cards/all")
    Observable<List<Card>> getCards();
    //загружает все по определенной теме
    @GET("/languageapp/cards/all/{theme}")
    Observable<List<Card>> getCardsByTheme(@Path("theme") String theme);
    //синхронизирует карточки на сервере и в приложении
    @POST("/languageapp/controller/update")
    Observable<Card> updateCards(@Header("Token") String token, @Body Card card);
    //отправляет новую карточку на сервер
    @POST("/languageapp/controller/post_new_card")
    Call<Card> uploadCards(@Header("Token") String token, @Body Card card);
    //авторизирует пользователя
    @POST("/languageapp/users/password")
    Observable<ResponseBody> getUserPassword(@Body User user);
    //повторный вход в приложение
    @POST("/languageapp/users/user/token")
    Observable<ResponseBody> getUserToken(@Body User user);
    //загружает карточки пользвоателя при повторной авторизации
    @POST("/languageapp/controller/user_cards")
    Observable<List<Card>> getUserCardsFromServer(@Header("Token") String token, @Body List<Card> cards);
    //синхронизирует данные с сервером
    @POST("/languageapp/controller/post_all_cards")
    Observable<ResponseBody> postAllCardsToServer(@Header("Token") String token, @Body List<Card> cards);
    //удаляет карточку пользователя
    @POST("/languageapp/controller/delete")
    Call<ResponseBody> deleteCard(@Header("Token") String token, @Body Card card);
    //отправляет данные пользователя при регистрации
    @POST("/languageapp/users/user")
    Observable<ResponseBody> loadUser(@Body User user);
}
