-- Criacao das bases de dados e usuarios padroes para o desenvolvimento.
--
-- Ver tambem: 'setup/sun-resources.xml' e 'src/conf/persistence.xml'
--
create database csocial;
create database csocial_test;
grant all privileges on csocial.* to 'csocial'@'%' identified by 'csocial';
grant all privileges on csocial_test.* to 'csocial'@'%' identified by 'csocial';

