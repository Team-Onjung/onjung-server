DELIMITER $$
DROP PROCEDURE IF EXISTS whileProc;
CREATE PROCEDURE whileProc()
    BEGIN
        SET  @i = 0;
        WHILE (@i < 10) DO
                insert into  user (
                                email,
                                uuid,
                                location_id,
                                provider,
                                profile_img,
                                profile_intro,
                                phone,
                                birth,
                                username,
                                university,
                                created_at,
                                updated_at,
                                last_logined,
                                is_active,
                                is_blocked,
                                is_university
                            )
                            values(
                                "email",
                                "uuid",
                                "locationId",
                                "provider",
                                "profileImg",
                                "profileIntro",
                                "phone",
                                now(),
                                CAST(@i as CHAR),
                                "university",
                                now(),
                                now(),
                                now(),
                                TRUE,
                                FALSE,
                                FALSE
                            );
                SET @i = @i+1;
        END WHILE;
    END $$
DELIMITER ;
CALL whileProc();