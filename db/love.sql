create table sys_user
(
    id          serial       not null,
    username    varchar(50)  not null,
    password    varchar(100) not null,
    nickname    varchar(50)  not null,
    avatar      varchar(200) not null,
    background  varchar(200),
    gender      int          not null,
    version     int          not null default 0,
    is_deleted  boolean      not null default false,
    creator     int,
    create_time timestamp    not null,
    updater     int,
    update_time timestamp    not null,
    primary key (id)
);

comment on table sys_user is '用户表';

comment on column sys_user.id is 'id';
comment on column sys_user.username is '用户名';
comment on column sys_user.password is '密码';
comment on column sys_user.nickname is '昵称';
comment on column sys_user.avatar is '头像';
comment on column sys_user.background is '背景图';
comment on column sys_user.gender is '性别';
comment on column sys_user.version is '版本号';
comment on column sys_user.is_deleted is '是否删除';
comment on column sys_user.creator is '创建者';
comment on column sys_user.create_time is '创建时间';
comment on column sys_user.updater is '更新者';
comment on column sys_user.update_time is '更新时间';

create table sys_user_token
(
    id                   serial      not null,
    user_id              int         not null,
    access_token         varchar(50) not null,
    access_token_expire  timestamp   not null,
    refresh_token        varchar(50) not null,
    refresh_token_expire timestamp   not null,
    create_time          timestamp   not null,
    primary key (id)
);

comment on table sys_user_token is '用户令牌表';

comment on column sys_user_token.id is 'id';
comment on column sys_user_token.user_id is '用户ID';
comment on column sys_user_token.access_token is '访问令牌';
comment on column sys_user_token.access_token_expire is '访问令牌 过期时间';
comment on column sys_user_token.refresh_token is '刷新令牌';
comment on column sys_user_token.refresh_token_expire is '刷新令牌 过期时间';
comment on column sys_user_token.create_time is '创建时间';

create table sys_user_group
(
    id          serial    not null,
    user1_id    int       not null,
    user2_id    int       not null,
    version     int       not null default 0,
    is_deleted  boolean   not null default false,
    creator     int,
    create_time timestamp not null,
    updater     int,
    update_time timestamp not null,
    check (user1_id <> user2_id) -- 确保 user1_id 和 user2_id 不相同
);

-- 创建唯一约束，确保 user1_id 和 user2_id 的组合在未删除的情况下是唯一的
create unique index idx_unique_user_ids
    on sys_user_group (least(user1_id, user2_id), greatest(user1_id, user2_id))
    where is_deleted = false;

comment on table sys_user_group is '用户分组表';

comment on column sys_user_group.id is 'id';
comment on column sys_user_group.user1_id is '用户1 ID';
comment on column sys_user_group.user2_id is '用户2 ID';
comment on column sys_user_group.version is '乐观锁';
comment on column sys_user_group.is_deleted is '是否删除';
comment on column sys_user_group.creator is '创建者';
comment on column sys_user_group.create_time is '创建时间';
comment on column sys_user_group.updater is '更新者';
comment on column sys_user_group.update_time is '更新时间';

create table poop_log
(
    id          serial    not null,
    user_id     int       not null,
    start_time  timestamp not null,
    duration    interval,
    poop_type   int       not null,
    version     int       not null default 0,
    is_deleted  boolean   not null default false,
    creator     int,
    create_time timestamp not null,
    updater     int,
    update_time timestamp not null,
    primary key (id)
);

comment on table poop_log is '便便记录表';

comment on column poop_log.id is 'id';
comment on column poop_log.user_id is '用户ID';
comment on column poop_log.start_time is '便便开始时间';
comment on column poop_log.duration is '便便时长';
comment on column poop_log.poop_type is '便便类型';
comment on column poop_log.version is '版本号';
comment on column poop_log.is_deleted is '是否删除 ';
comment on column poop_log.creator is '创建者';
comment on column poop_log.create_time is '创建时间';
comment on column poop_log.updater is '更新者';
comment on column poop_log.update_time is '更新时间';

create table poop_summary
(
    id            serial     not null,
    month         varchar(7) not null,
    user_id       int        not null,
    poop_count    int        not null default 0,
    winner_status boolean    not null default false,
    reward_status boolean    not null default false,
    reward_image  varchar(255),
    version       int        not null default 0,
    is_deleted    boolean    not null default false,
    creator       int,
    create_time   timestamp  not null,
    updater       int,
    update_time   timestamp  not null,
    primary key (id),
    unique (month, user_id)
);

comment on table poop_summary is '按月统计用户便便的核算表';

comment on column poop_summary.id is 'id';
comment on column poop_summary.month is '统计月份，格式为 YYYY-MM';
comment on column poop_summary.user_id is '用户ID';
comment on column poop_summary.poop_count is '用户当月的便便数量';
comment on column poop_summary.winner_status is '是否为当月冠军';
comment on column poop_summary.reward_status is '是否领取奖品';
comment on column poop_summary.reward_image is '奖品图片名称';
comment on column poop_summary.version is '乐观锁';
comment on column poop_summary.is_deleted is '是否删除';
comment on column poop_summary.creator is '创建者';
comment on column poop_summary.create_time is '创建时间';
comment on column poop_summary.updater is '更新者';
comment on column poop_summary.update_time is '更新时间';

