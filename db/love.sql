create table sys_user
(
    id          serial       not null,
    username    varchar(50)  not null,
    password    varchar(100) not null,
    nickname    varchar(50)  not null,
    avatar      varchar(200),
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
comment on column sys_user_token.user_id is '用户id';
comment on column sys_user_token.access_token is '访问令牌';
comment on column sys_user_token.access_token_expire is '访问令牌 过期时间';
comment on column sys_user_token.refresh_token is '刷新令牌';
comment on column sys_user_token.refresh_token_expire is '刷新令牌 过期时间';
comment on column sys_user_token.create_time is '创建时间';

create table sys_dict_type
(
    id          serial       not null,
    dict_type   varchar(100) not null,
    dict_label  varchar(255) not null,
    dict_value  int          not null,
    remark      varchar(255),
    sort        int,
    version     int          not null default 0,
    is_deleted  boolean      not null default false,
    creator     int,
    create_time timestamp    not null,
    updater     int,
    update_time timestamp    not null,
    primary key (id)
);

comment on table sys_dict_type is '字典类型表';
comment on column sys_dict_type.id is 'id';
comment on column sys_dict_type.dict_type is '字典类型';
comment on column sys_dict_type.dict_label is '字典标签（显示值）';
comment on column sys_dict_type.dict_value is '字典值';
comment on column sys_dict_type.remark is '备注';
comment on column sys_dict_type.sort is '排序';
comment on column sys_dict_type.version is '版本号';
comment on column sys_dict_type.is_deleted is '是否删除 ';
comment on column sys_dict_type.creator is '创建者';
comment on column sys_dict_type.create_time is '创建时间';
comment on column sys_dict_type.updater is '更新者';
comment on column sys_dict_type.update_time is '更新时间';

create table poop_log
(
    id            serial    not null,
    user_id       int       not null,
    log_time      timestamp not null,
    poop_log_type int       not null,
    version       int       not null default 0,
    is_deleted    boolean   not null default false,
    creator       int,
    create_time   timestamp not null,
    updater       int,
    update_time   timestamp not null,
    primary key (id)
);

comment on table poop_log is '粪便记录表';
comment on column poop_log.id is 'id';
comment on column poop_log.log_time is '记录时间';
comment on column poop_log.poop_log_type is '粪便类型';
comment on column poop_log.version is '版本号';
comment on column poop_log.is_deleted is '是否删除 ';
comment on column poop_log.creator is '创建者';
comment on column poop_log.create_time is '创建时间';
comment on column poop_log.updater is '更新者';
comment on column poop_log.update_time is '更新时间';
