Projekt api dla forum internetowego.

End pointy i ich opis:

Legenda:

Entity: /url:

/endpoint/{variable} - description [role|method|request_body]

Category: /category:

/{id} - pobiera kategorie po id.
/create - tworzy nową kategorie.
/delete/{id} - usuwa kategorie po id.
/rename/{id} - zmienia nazwe kategorii po id.

Comment: /comment:

/{id} - pobiera komentarz po id.
/create - tworzy nowy komentarz dla postu.
/edit/{id} - edytuje zawartość komentarza po id.
/delete/{id} - usuwa komentarz po id.
/like/{id} - dodaje like do komentarza po id.
/unlike/{id} - usuwa like z komentarza po id.

Group: /group:

/authorities - pobiera liste nazw uprawnien dostepnych w projekcie.
/all - pobiera wszystkie grupy.
/{id} - pobiera grupe po id.
/create - tworzy nową grupe.
/delete/{id} - usuwa grupe po id.
