基本功能
一、记事本编辑功能

1.记事本的标题与内容编写，保存；

点击添加笔记按钮

![image](https://github.com/user-attachments/assets/08ed1b02-2334-45ab-bbf0-2a9a4345c9b3)

输入标题和内容点击保存（会伴随 toast提示）

![image](https://github.com/user-attachments/assets/dd97deab-e3d2-4b38-90ee-c3dd27476ff0)

可以看到笔记‘hello world’保存成功

![c14d3ff4765fbf94468fad8daae63629](https://github.com/user-attachments/assets/1192cf98-1fb6-4a3b-872d-d8ca09cae8e4)

2.每条笔记可删除或者再次编辑；

点击已经保存的笔记“hello world”，进入笔记的编辑页面

![7ae42c4b3d01c3e849cdea8cd4163e9a](https://github.com/user-attachments/assets/7252abf9-bb3e-4254-b9f5-8a79d4c3c8cb)

修改笔记内容与标题并点击保存，可以看到笔记被修改成功

![11e74b37b45e105c28adaa6fc55d0ac6](https://github.com/user-attachments/assets/539ce0ce-2971-4efc-9452-b430f27baff2)

再次点开点击笔记“hello world1”，进入笔记编辑界面，点击删除按钮，可以看到笔记“hello world1”被删除成功（会伴随toast提示）

![5706585032ee694076a27ba6afa03d41](https://github.com/user-attachments/assets/278cd14a-181b-453b-8f5d-ba4ce5ecb456)

3.时间戳显示

可以看到笔记“today”的时间戳显示为“24-11-28 07-14”（此日期为笔记“today”的最后编辑日期）

![image](https://github.com/user-attachments/assets/c1ab2e1f-448b-44bc-b0e6-6cd5cc3e1e16)

点击笔记“today”，进入笔记编辑页面

![image](https://github.com/user-attachments/assets/3b63efb0-38c4-45bf-942a-fdf03903d0fa)

修改笔记内容后点击保存，可以看到笔记“today”的时间戳更新为现在的“24-12-01 04-26”，说明笔记的时间戳显示的是最后的编辑时间

![image](https://github.com/user-attachments/assets/dbbf357f-569c-456e-a884-79aa6af1bf20)

![image](https://github.com/user-attachments/assets/a8f39bd6-5d50-4b2b-9af3-87b0ed6b0871)

二、首页

1.所有笔记的列表展示功能

笔记的首页将所有的笔记以列表的形式展示

![image](https://github.com/user-attachments/assets/bab95f18-680d-420b-aebc-90931f3d466f)

2.笔记可以搜索，去匹配数据库的所有数据，筛选后进行展示

笔记的首页的上方可以进行搜索功能，我们先创建三个笔记，创建的笔记如下：

![image](https://github.com/user-attachments/assets/06bb5899-f3ed-403d-817a-f23a289a9124)

点击搜索按钮输入“111”，可以看到标题为“111”的笔记被搜索出来，点击可以直接进入笔记“111”的编辑页面

![image](https://github.com/user-attachments/assets/84e19682-b2db-4c5d-a0ab-c7b08ae29312)

![image](https://github.com/user-attachments/assets/3fe29a02-ddd4-44f9-87e6-d6a2d4909f50)

扩展功能

一、ui美化

1.新增更改笔记背景功能

进入笔记“111”的编辑界面

![image](https://github.com/user-attachments/assets/a91f9728-8e3b-4c6f-876f-bd177a612a76)

点击白色右侧的三角按钮，可以看到背景颜色更改的菜单，有三种颜色可以选择：白色（默认）、灰色、黄色

![image](https://github.com/user-attachments/assets/d086933d-dd06-4005-b0ee-c911af9246d7)

点击灰色，可以看到笔记的背景颜色变为灰色

![image](https://github.com/user-attachments/assets/6e1f8ec3-08d8-4cf4-8381-64c36b5cb0d7)

点击黄色，可以看到笔记的背景颜色变为黄色

![image](https://github.com/user-attachments/assets/d4d32b65-1eb4-45ab-81ba-7b79a4d348fe)

2.新增更改笔记字体大小功能

进入笔记“111”的编辑界面

![image](https://github.com/user-attachments/assets/1a1b5b1b-e4c5-4381-a98c-036c8f7e5ac5)

点击中右侧的三角按钮，可以看到字体大小更改的菜单，有三种字体可以选择：小、中(默认)、大

![image](https://github.com/user-attachments/assets/4e5defd2-03fc-4ff4-b6ac-746f5dd8713a)

点击小，可以看到笔记内容的字体大小变为小

![image](https://github.com/user-attachments/assets/694259ec-c662-4b66-9d3e-bc0fc7baf23a)

点击大，可以看到笔记内容的字体大小变为大

![image](https://github.com/user-attachments/assets/37ebabf0-5b55-4666-92ca-ded009378717)

3.ui按钮美化

将新增笔记的按钮放在笔记首页的最下方（显眼且美观）

![image](https://github.com/user-attachments/assets/7c3c9dc0-3e01-4373-9a86-b349a4372338)

将搜索按钮放在笔记首页的最上方（显眼且美观）

![image](https://github.com/user-attachments/assets/1268ef90-8d54-428f-b425-adc6f7a1dc78)

将保存和删除按钮放在编辑笔记界面的最下方（显眼且美观）

![image](https://github.com/user-attachments/assets/2eed936a-f3b6-4838-a383-d3effae7150a)

4.ui界面美化

将编辑笔记界面的标题与内容上下分层，更改选项放在其二中间（层次分明）

![image](https://github.com/user-attachments/assets/e8beb5c4-7196-4b63-b112-c6db1556becb)

二、代办功能

在笔记首页点击笔记“111”和“222”的勾选按钮，笔记“111”和“222”的背景变深，表示代办完成（弹出toast：已完成代办）

![image](https://github.com/user-attachments/assets/46b810bb-db2c-4db7-bd5c-f0b55b1fb05c)

三、分类功能

在笔记的编辑界面，可以对笔记进行分类

![image](https://github.com/user-attachments/assets/583180a7-758c-4723-829f-35c63f9df775)

点击学习右侧的三角按钮，可以看到分类更改的菜单，有三种分类可以选择：学习（默认）、工作、生活

![image](https://github.com/user-attachments/assets/604f6e5c-9252-4f42-b851-735f6c552bb2)

点击学习，可以看到笔记“111”被分类为学习

![f56fbee0-c8b1-43b9-a161-78ab40d1968b](https://github.com/user-attachments/assets/731935f3-b61a-4216-b9b9-e4d3deb6f76d)

点击工作，可以看到笔记“222”被分类为学习

![image](https://github.com/user-attachments/assets/8d15ef01-1fd2-45c7-ac8e-dd8d7e51478a)

点击生活，可以看到笔记“333”被分类为生活

![image](https://github.com/user-attachments/assets/8a01c149-5bba-4444-be18-afb5abdb78bd)

点击保存后可以看到笔记首页中，三个笔记的分类被修改

![image](https://github.com/user-attachments/assets/2b2266f5-a35d-48d5-81c5-33914247f263)

技术框架

1.布局主要选用线性布局，因为满足需求的情况下，此布局消耗资源少，部分选用约束布局

2.数据库的存储使用SQLite数据库，是Android原生自带的数据库，安全，高校

3.关于编辑器的字体和背景设置，采用sharedPreferences存储到本地，sp存储适合存储app的一些基础配置

优化点：

首页列表的NotesAdapter里，我选择在NoteViewHolder里进行view的绑定，避免了在onBindViewHolder进行绑定因为recycleView的缓存刷新机制，这样可以减少findViewById的IO操作，提高性能，减少内存消耗；
