Projekt api dla forum internetowego.

End pointy i ich opis:

Legenda:

Entity: /url:

/endpoint/{variable} - description [role|method|request_body]

Category: /category:

/{id} - pobiera kategorie po id. [none|get|none]
/create - tworzy nową kategorie. [admin|post|json]
/delete/{id} - usuwa kategorie po id. [admin|delete|none]
/rename/{id} - zmienia nazwe kategorii po id. [admin|patch|json]

Comment: /comment:

/{id} - pobiera komentarz po id. [none|get|none]
/create - tworzy nowy komentarz dla postu. [user|psot|json]
/edit/{id} - edytuje zawartość komentarza po id. [user|patch|json]
/delete/{id} - usuwa komentarz po id. [user|delete|none]
/like/{id} - dodaje like do komentarza po id. [user|post|none]
/unlike/{id} - usuwa like z komentarza po id. [user|delete|none]

Group: /group:

/authorities - pobiera liste nazw uprawnien dostepnych w projekcie. [admin|get|none]
/all - pobiera wszystkie grupy. [admin|get|none]
/{id} - pobiera grupe po id. [admin|get|none]
/create - tworzy nową grupe. [admin|post|json]
/delete/{id} - usuwa grupe po id. [admin|delete|none]
/rename/{id} - zmienia nazwe grupy po id. [admin|patch|json]
/change/{id} - zmienia uprawnienia grupy po id. [admin|patch|json]

Post: /post:

/{id} - pobiera posta po id. [none|get|none]
/search/{content} - wyszukuje posta po zawartosci. [none|get|none]
/lastActive - pobiera 10 ostatnio aktywnych postów. [none|get|none]
/mostLiked - pobiera 10 najpopularniejszych postów. [none|get|none]
/create - tworzy nowego posta. [user|post|json]
/delete/{id} - usuwa posta po id. [user|delete|none]
/edit/{id} - edytuje tytul i treść posta po id. [user|patch|json]
/like/{id} - dodaje like do posta po id. [user|post|none]
/unlike/{id} - usuwa like z posta po id. [user|delete|none]

Report: /report:

/{id} - pobiera zgłoszenie po id. [user|get|none]
/post/{post_id} - tworzy nowe zgłoszenie do posta po id. [user|post|json]
/comment/{comment_id} - tworzy nowe zgłoszenie do komentarza po id. [user|post|json]
/check/{id} - zmienia status zgłoszenia na sprawdzone. [moderator|patch|none]
/notchecked - pobiera wszystkie nie sprawdzone zgłoszenia. [moderator|get|none]
/delete - usuwa zgłoszenie po id. [admin|delete|none]

Section: /section:

/{id} - pobiera sekcje po id. [none|get|none]
/all - pobiera wszystkie sekcje. [none|get|none]
/create - tworzy nową sekcje. [admin|post|json]
/delete/{id} - usuwa sekcjie po id. [admin|delete|none]
/rename/{id} - zmienia nazwe sekcji po id. [admin|patch|json]

com.revo.myboard.security: /:

/login - zwraca token dla poprawnej autoryzacji. [none|post|json]
/register - tworzy nowego użytkownika. [none|post|json]
/active - aktywuje konto użytkownika po kodzie. [none|patch|json]
/active/{login} - aktywuje konto użytkownika po loginie. [admin|patch|none]
/resendActivationCode - ponownie wysyła kod aktywacyjny na email. [none|patch|json]

User: /user:

/{login} - pobiera użytkownika po loginie. [none|get|none]
/search/{content} - wyszukuje użytkowników po zawartości. [none|get|none]
/credentials - tłumaczy token użytkownika na jego dane. [none|get|none]
/change/password - zmienia hasło użytkownika. [user|patch|json]
/change/email - zmienia email użytkownika. [user|patch|json]
/change/data - zmienia dane profilowe użytkownika. [user|patch|json]
/profile - pobiera profil użytkownika po tokenie. [user|patch|none]
/delete/{login} - usuwa konto użytkownika po loginie. [user|patch|none]
/sexes - pobiera liste nazw płci dostępnych w projekcie. [user|get|none]
/ban/{login} - banuje użytkownika po loginie. [moderator|patch|none]
/unban/{login} - odbanowywuje użytkownika po loginie. [moderator|patch|none]
/group/{login}/{group_id} - zmienia grupe użytkownika na podstawie id i loginu. [admin|patch|none]
