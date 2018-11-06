create procedure GetUsersAttachedToTask(IN TaskID int)
  begin
select u.id,u.username,u.email,u.pass_hash,u.priv_lvl from users u, user_task_assigned uta
where u.id = uta.user_id and uta.task_id = TaskID;
end;

