# 基本功能
## 一、记事本编辑功能

### 1.记事本的标题与内容编写，保存；

点击添加笔记按钮

![image](https://github.com/user-attachments/assets/08ed1b02-2334-45ab-bbf0-2a9a4345c9b3)

输入标题和内容点击保存（会伴随 toast提示）

![image](https://github.com/user-attachments/assets/dd97deab-e3d2-4b38-90ee-c3dd27476ff0)

可以看到笔记‘hello world’保存成功

![c14d3ff4765fbf94468fad8daae63629](https://github.com/user-attachments/assets/1192cf98-1fb6-4a3b-872d-d8ca09cae8e4)

核心代码：

    private fun saveNote() {
        val title = editTextTitle.text.toString().trim()
        val content = editTextNote.text.toString().trim()
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and content cannot be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (noteId == -1L) {
            dbHelper.insertNote(Note(0, title, content, NormalUtils.formatTimestampToYYMMDD(System.currentTimeMillis()), noteType,false))
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.updateNote(Note(noteId, title, content, NormalUtils.formatTimestampToYYMMDD(System.currentTimeMillis()), noteType,false))
            Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

核心代码分析：

获取用户输入：

    val title = editTextTitle.text.toString().trim()
    val content = editTextNote.text.toString().trim()

从用户输入框 editTextTitle 和 editTextNote 获取标题和内容的文本值，并去除前后空格。trim() 方法用于去掉输入值的空格，确保数据的整洁。

校验输入：

    if (title.isEmpty() || content.isEmpty()) {
    Toast.makeText(this, "Title and content cannot be empty!", Toast.LENGTH_SHORT).show()
    return
    }

在保存之前，首先检查用户是否输入了标题和内容。如果标题或内容为空，弹出提示框告诉用户“标题和内容不能为空”，然后通过 return 语句直接结束函数，阻止保存操作。

插入、更新笔记：

    if (noteId == -1L) {
            dbHelper.insertNote(Note(0, title, content, NormalUtils.formatTimestampToYYMMDD(System.currentTimeMillis()), noteType,false))
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.updateNote(Note(noteId, title, content, NormalUtils.formatTimestampToYYMMDD(System.currentTimeMillis()), noteType,false))
            Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
        }

插入操作：

如果 noteId 为 -1L，表示这是一个新笔记，尚未存在于数据库中，执行插入操作。

调用 dbHelper.insertNote() 插入一个新的 Note 对象。Note 构造函数中传入的参数包括：

  0：笔记的ID。

  title 和 content：用户输入的标题和内容。

  noteType：笔记类型。

  false：标记是否已完成，是笔记的状态（例如是否完成编辑或已删除）。

保存后，通过 Toast 提示用户“Note saved!”表示保存成功。

更新操作：

如果 noteId 不是 -1L，表示这是对已存在的笔记进行更新。

调用 dbHelper.updateNote() 更新笔记，其中传入了原有 noteId 和更新后的信息（标题、内容、时间、类型等）。

保存后，通过 Toast 提示用户“Note updated!”表示更新成功。

### 2.每条笔记可删除或者再次编辑；

点击已经保存的笔记“hello world”，进入笔记的编辑页面

![7ae42c4b3d01c3e849cdea8cd4163e9a](https://github.com/user-attachments/assets/7252abf9-bb3e-4254-b9f5-8a79d4c3c8cb)

修改笔记内容与标题并点击保存，可以看到笔记被修改成功

![11e74b37b45e105c28adaa6fc55d0ac6](https://github.com/user-attachments/assets/539ce0ce-2971-4efc-9452-b430f27baff2)

再次点开点击笔记“hello world1”，进入笔记编辑界面，点击删除按钮，可以看到笔记“hello world1”被删除成功（会伴随toast提示）

![5706585032ee694076a27ba6afa03d41](https://github.com/user-attachments/assets/278cd14a-181b-453b-8f5d-ba4ce5ecb456)

### 3.时间戳显示

可以看到笔记“today”的时间戳显示为“24-11-28 07-14”（此日期为笔记“today”的最后编辑日期）

![image](https://github.com/user-attachments/assets/c1ab2e1f-448b-44bc-b0e6-6cd5cc3e1e16)

点击笔记“today”，进入笔记编辑页面

![image](https://github.com/user-attachments/assets/3b63efb0-38c4-45bf-942a-fdf03903d0fa)

修改笔记内容后点击保存，可以看到笔记“today”的时间戳更新为现在的“24-12-01 04-26”，说明笔记的时间戳显示的是最后的编辑时间

![image](https://github.com/user-attachments/assets/dbbf357f-569c-456e-a884-79aa6af1bf20)

![image](https://github.com/user-attachments/assets/a8f39bd6-5d50-4b2b-9af3-87b0ed6b0871)

核心代码：

1>获取当前时间：

    NormalUtils.formatTimestampToYYMMDD(System.currentTimeMillis()

System.currentTimeMillis() 会返回当前系统时间的 时间戳（即当前时间的毫秒数）。

2>将传入的时间戳（timestamp）转换为指定格式的日期字符串:

    object NormalUtils {

    fun formatTimestampToYYMMDD(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yy-MM-dd hh-mm", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    }

核心代码分析：

formatTimestampToYYMMDD 函数：

    fun formatTimestampToYYMMDD(timestamp: Long): String 
    
该函数接受一个类型为 Long 的参数 timestamp，表示自1970年1月1日以来的时间戳（毫秒数）。

返回值是一个 String 类型的日期格式化字符串。

SimpleDateFormat 用法：

    val dateFormat = SimpleDateFormat("yy-MM-dd hh-mm", Locale.getDefault())
    
SimpleDateFormat 是一个日期格式化类，用于将时间戳或 Date 对象转换为指定格式的字符串。

"yy-MM-dd hh-mm" 是指定的日期时间格式：
yy: 年份的后两位

MM: 月份

dd: 日期

hh: 12小时制的小时

mm: 分钟

Locale.getDefault() 获取当前设备的默认语言区域，这样可以根据设备的地区设置适当的格式。

格式化日期：

    return dateFormat.format(Date(timestamp))
    
Date(timestamp) 将时间戳转换为 Date 对象。

dateFormat.format() 将 Date 对象按照指定格式（yy-MM-dd hh-mm）转换成字符串。

## 二、首页

### 1.所有笔记的列表展示功能

笔记的首页将所有的笔记以列表的形式展示

![image](https://github.com/user-attachments/assets/bab95f18-680d-420b-aebc-90931f3d466f)

### 2.笔记可以搜索，去匹配数据库的所有数据，筛选后进行展示

笔记的首页的上方可以进行搜索功能，我们先创建三个笔记，创建的笔记如下：

![image](https://github.com/user-attachments/assets/06bb5899-f3ed-403d-817a-f23a289a9124)

点击搜索按钮输入“111”，可以看到标题为“111”的笔记被搜索出来，点击可以直接进入笔记“111”的编辑页面

![image](https://github.com/user-attachments/assets/84e19682-b2db-4c5d-a0ab-c7b08ae29312)

![image](https://github.com/user-attachments/assets/3fe29a02-ddd4-44f9-87e6-d6a2d4909f50)

核心代码：

    fun searchNotes(query: String): List<Note> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_TITLE LIKE ?",
            arrayOf("%$query%"),
            null,
            null,
            "$COLUMN_MODIFICATION_DATE DESC"
        )
        val notes = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            notes.add(
                Note(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    modificationDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODIFICATION_DATE)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                    isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_IS_DONE)) == 1
                )
            )
        }
        cursor.close()
        return notes
    }

核心代码分析：

1>查询数据库

    val db = readableDatabase
    val cursor = db.query(
    TABLE_NAME,
    null,
    "$COLUMN_TITLE LIKE ?",
    arrayOf("%$query%"),
    null,
    null,
    "$COLUMN_MODIFICATION_DATE DESC"
    )
    
readableDatabase：使用数据库的只读操作来进行查询。

db.query：执行查询操作，查询 TABLE_NAME 表中的数据。

"$COLUMN_TITLE LIKE ?"：查询条件是 COLUMN_TITLE 字段包含用户输入的 query 字符串。

arrayOf("%$query%")：这是查询的参数，将 query 包裹在 % 符号中，用于模糊匹配，即查询标题中包含查询关键字的笔记。

"$COLUMN_MODIFICATION_DATE DESC"：查询结果按 COLUMN_MODIFICATION_DATE 字段（修改时间）降序排列。

3. 处理查询结果

        val notes = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            notes.add(
                Note(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    modificationDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODIFICATION_DATE)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                    isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_IS_DONE)) == 1
                )
            )
        }
        cursor.close()

cursor.moveToNext()：遍历查询结果的每一行。

cursor.getColumnIndexOrThrow(COLUMN_X)：根据列名获取对应列的索引，并从 cursor 中取出值，创建 Note 对象。

id, title, content, modificationDate, type, isDone 等字段分别对应 Note 类的属性。

notes.add()：将查询到的每一条笔记添加到 notes 列表中。

# 扩展功能

## 一、ui美化

### 1.新增更改笔记背景功能

进入笔记“111”的编辑界面

![image](https://github.com/user-attachments/assets/a91f9728-8e3b-4c6f-876f-bd177a612a76)

点击白色右侧的三角按钮，可以看到背景颜色更改的菜单，有三种颜色可以选择：白色（默认）、灰色、黄色

![image](https://github.com/user-attachments/assets/d086933d-dd06-4005-b0ee-c911af9246d7)

点击灰色，可以看到笔记的背景颜色变为灰色

![image](https://github.com/user-attachments/assets/6e1f8ec3-08d8-4cf4-8381-64c36b5cb0d7)

点击黄色，可以看到笔记的背景颜色变为黄色

![image](https://github.com/user-attachments/assets/d4d32b65-1eb4-45ab-81ba-7b79a4d348fe)

核心代码：

        bgColorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val color = when (position) {
                    0 -> Color.WHITE
                    1 -> Color.LTGRAY
                    2 -> Color.YELLOW
                    else -> Color.WHITE
                }
                editTextNote.setBackgroundColor(color)
                saveBackgroundColorPreference(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        
        val bgColorPosition = sharedPreferences.getInt("bg_color_position", 0)
        bgColorSpinner.setSelection(bgColorPosition)
        editTextNote.setBackgroundColor(
            when (bgColorPosition) {
                0 -> Color.WHITE
                1 -> Color.LTGRAY
                2 -> Color.YELLOW
                else -> Color.WHITE
            }
        )

    private fun saveBackgroundColorPreference(position: Int) {
        sharedPreferences.edit().putInt("bg_color_position", position).apply()
    }
    
核心代码分析：

设置了 OnItemSelectedListener，意味着每当用户从下拉框中选择一个项目时，都会触发 onItemSelected 方法。

onItemSelected 方法：这是一个回调方法，会在用户选择某项时调用，包含以下参数：

parent：触发事件的 AdapterView（在此为 bgColorSpinner）。

view：与被选中项对应的视图元素。

position：用户选中的项的位置（索引），从 0 开始。

id：被选中项的 ID，通常不需要使用。

editTextNote.setBackgroundColor(color)：根据 position 的选择，设置 editTextNote（编辑框）背景颜色为对应的颜色值（color）。editTextNote 应该是一个 EditText 控件，用于用户输入笔记内容。通过 setBackgroundColor 方法修改该控件的背景色。

saveBackgroundColorPreference(position)：这个方法用于保存用户选择的背景颜色。position 会被保存下来，表示用户的选择项。通常，这个方法会将 position 存储到本地存储中，以便在下次应用启动时可以恢复用户的颜色选择。

从 SharedPreferences 中读取名为 "bg_color_position" 的整型值，该值表示当前选择的背景颜色的位置，默认值为 0（白色背景）。

bgColorSpinner.setSelection(bgColorPosition)：根据保存的设置，设置 bgColorSpinner 下拉框的选中项。

根据 bgColorPosition 的值，设置 editTextNote 编辑框的背景颜色：

0：白色背景。

1：浅灰色背景。

2：黄色背景。

将背景颜色的位置（position）保存到 SharedPreferences 中，键为 "bg_color_position"。

### 2.新增更改笔记字体大小功能

进入笔记“111”的编辑界面

![image](https://github.com/user-attachments/assets/1a1b5b1b-e4c5-4381-a98c-036c8f7e5ac5)

点击中右侧的三角按钮，可以看到字体大小更改的菜单，有三种字体可以选择：小、中(默认)、大

![image](https://github.com/user-attachments/assets/4e5defd2-03fc-4ff4-b6ac-746f5dd8713a)

点击小，可以看到笔记内容的字体大小变为小

![image](https://github.com/user-attachments/assets/694259ec-c662-4b66-9d3e-bc0fc7baf23a)

点击大，可以看到笔记内容的字体大小变为大

![image](https://github.com/user-attachments/assets/37ebabf0-5b55-4666-92ca-ded009378717)

核心代码：

        fontSizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val textSize = when (position) {
                    0 -> 14f
                    1 -> 18f
                    2 -> 22f
                    else -> 18f
                }
                editTextNote.textSize = textSize
                saveFontSizePreference(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        
        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                noteType =when(position){
                    0->"学习"
                    1->"工作"
                    2->"生活"
                    else ->"其他"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

        val fontSizePosition = sharedPreferences.getInt("font_size_position", 1)
        fontSizeSpinner.setSelection(fontSizePosition)
        editTextNote.textSize = when (fontSizePosition) {
            0 -> 14f
            1 -> 18f
            2 -> 22f
            else -> 18f
        }

     private fun saveFontSizePreference(position: Int) {
        sharedPreferences.edit().putInt("font_size_position", position).apply()
    }
        
核心代码分析:

fontSizeSpinner.onItemSelectedListener：为字体大小选择的 Spinner 设置监听器。当用户选择不同的项时，会调用 onItemSelected 方法。

onItemSelected：这是一个回调方法，触发条件是用户选择了 Spinner 中的一项。

position：表示用户选择的项的位置（从 0 开始）。

textSize：根据 position 的值，选择不同的字体大小（14f、18f、22f）。如果选择项的 position 不在预期范围内，默认字体大小为 18f。

设置字体大小：editTextNote.textSize = textSize 将计算出的 textSize 应用到编辑框 editTextNote 上，调整笔记的显示字体大小。

保存偏好设置：saveFontSizePreference(position) 会保存用户的选择，以便在下次启动应用时可以记住用户的偏好设置。将选中的 position 保存在数据库中。

onNothingSelected：这是 OnItemSelectedListener 接口的另一个回调方法，但在这个例子中没有使用。当 Spinner 中没有选择任何项时会触发该方法，可以留空或添加一些默认行为。

从 SharedPreferences 中读取名为 "font_size_position" 的整型值，该值表示当前选择的字体大小的位置，默认值为 1（中号字体）。

fontSizeSpinner.setSelection(fontSizePosition)：根据保存的设置，设置 fontSizeSpinner 下拉框的选中项。

根据 fontSizePosition 的值，设置 editTextNote 编辑框的字体大小：

0：字体大小为 14sp。

1：字体大小为 18sp。

2：字体大小为 22sp。

将字体大小的位置（position）保存到 SharedPreferences 中，键为 "font_size_position"。

背景颜色和字号大小更改的关键在于：

使用 SharedPreferences 存储用户的偏好设置，使得这些设置在应用重新启动后仍然能够被恢复。

使用 apply() 方法进行保存，这种方式适用于不需要立即知道保存结果的场景，异步执行操作，性能较好。

### 3.ui按钮美化

将新增笔记的按钮放在笔记首页的最下方（显眼且美观）

![image](https://github.com/user-attachments/assets/7c3c9dc0-3e01-4373-9a86-b349a4372338)

    <androidx.appcompat.widget.AppCompatTextView
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:textColor="@color/white"
        android:background="@drawable/action_next_bg_c_30"
        android:textSize="17sp"
        android:id="@+id/addButton"
        android:layout_marginHorizontal="30dp"
        android:gravity="center"
        android:text="新增笔记"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_marginBottom="22.5dp"/>

将搜索按钮放在笔记首页的最上方（显眼且美观）

![image](https://github.com/user-attachments/assets/1268ef90-8d54-428f-b425-adc6f7a1dc78)

    <EditText
        android:layout_marginTop="30dp"
        android:layout_marginHorizontal="12dp"
        android:id="@+id/searchEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="搜索笔记"
        android:padding="8dp"/>

将保存和删除按钮放在编辑笔记界面的最下方（显眼且美观）

![image](https://github.com/user-attachments/assets/2eed936a-f3b6-4838-a383-d3effae7150a)

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="8dp">

        <TextView
            android:layout_marginHorizontal="20dp"
            android:id="@+id/btnSave"
            android:layout_width="0dp"
            android:layout_height="50dp"
            android:layout_weight="1"
            android:textColor="@color/white"
            android:gravity="center"
            android:background="@drawable/action_next_bg_c_30"
            android:text="保存" />

        <TextView
            android:id="@+id/btnDelete"
            android:layout_marginHorizontal="20dp"
            android:layout_width="0dp"
            android:layout_height="50dp"
            android:layout_weight="1"
            android:textColor="@color/white"
            android:gravity="center"
            android:background="@drawable/action_next_bg_c_30"
            android:text="删除" />
    </LinearLayout>

### 4.ui界面美化

将编辑笔记界面的标题与内容上下分层，更改选项放在其二中间（层次分明）

![image](https://github.com/user-attachments/assets/e8beb5c4-7196-4b63-b112-c6db1556becb)

    <LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <androidx.appcompat.widget.AppCompatTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:layout_marginTop="10dp"
        android:text="内容编辑"
        android:textSize="16sp"
        android:textStyle="bold"/>
    <!-- 标题输入框 -->
    <EditText
        android:layout_marginTop="3dp"
        android:id="@+id/editTextTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="请输入标题"
        android:padding="8dp"
        android:textSize="16sp" />

    <LinearLayout
        android:layout_marginVertical="4dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        >

        <TextView
            android:id="@+id/bgColorSpinnerti"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:textSize="15sp"
            android:textColor="@color/black"
            android:text="背景"
            android:entries="@array/bg_color_options" />
        <View
            android:layout_width="1dp"
            android:layout_height="match_parent"
            android:background="#4d000000"/>

        <TextView
            android:text="字号"
            android:gravity="center"
            android:id="@+id/fontSizeSpinnerti"
            android:layout_width="0dp"
            android:textSize="15sp"
            android:textColor="@color/black"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:entries="@array/font_size_options" />
        <View
            android:layout_width="1dp"
            android:layout_height="match_parent"
            android:background="#4d000000"/>

        <TextView
            android:text="类型"
            android:gravity="center"
            android:id="@+id/typeSpinnerti"
            android:layout_width="0dp"
            android:textSize="15sp"
            android:textColor="@color/black"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:entries="@array/font_size_options" />
    </LinearLayout>

    <!-- 编辑器设置区域 -->
    <LinearLayout
        android:layout_marginBottom="4dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="8dp">

        <Spinner
            android:id="@+id/bgColorSpinner"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:entries="@array/bg_color_options" />

        <Spinner
            android:id="@+id/fontSizeSpinner"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:entries="@array/font_size_options" />
        <Spinner
            android:id="@+id/typeSpinner"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:entries="@array/type_options" />
    </LinearLayout>

    <!-- 笔记内容编辑框 -->
    <EditText
        android:id="@+id/editTextNote"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:gravity="top"
        android:padding="16dp"
        android:hint="请输入内容..." />

    <!-- 操作按钮 -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="8dp">

        <TextView
            android:layout_marginHorizontal="20dp"
            android:id="@+id/btnSave"
            android:layout_width="0dp"
            android:layout_height="50dp"
            android:layout_weight="1"
            android:textColor="@color/white"
            android:gravity="center"
            android:background="@drawable/action_next_bg_c_30"
            android:text="保存" />

        <TextView
            android:id="@+id/btnDelete"
            android:layout_marginHorizontal="20dp"
            android:layout_width="0dp"
            android:layout_height="50dp"
            android:layout_weight="1"
            android:textColor="@color/white"
            android:gravity="center"
            android:background="@drawable/action_next_bg_c_30"
            android:text="删除" />
    </LinearLayout>
    </LinearLayout>

## 二、代办功能

在笔记首页点击笔记“111”和“222”的勾选按钮，笔记“111”和“222”的背景变深，表示代办完成（弹出toast：已完成代办）

![image](https://github.com/user-attachments/assets/46b810bb-db2c-4db7-bd5c-f0b55b1fb05c)

核心代码：

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.dateTextView.text = note.modificationDate
        holder.todoCheckBox.isChecked = note.isDone
        holder.type.text = note.type
        if (note.isDone) holder.rootView.setBackgroundColor(Color.parseColor("#D4E2E6"))
        else holder.rootView.setBackgroundColor(Color.parseColor("#FFFFFF"))

        holder.itemView.setOnClickListener {
            onNoteClicked(note)
        }

        holder.todoCheckBox.setOnCheckedChangeListener { _, isChecked ->

            onCheckChanged(note,isChecked)
            if (isChecked) holder.rootView.setBackgroundColor(Color.parseColor("#D4E2E6"))
            else holder.rootView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    adapter = NotesAdapter(
    notes = mutableListOf(),
    onNoteClicked = { note ->
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    },
    onCheckChanged = { note, ischeck ->
        val updatedNote = note.copy(isDone = ischeck)  // 更新笔记的完成状态
        dbHelper.updateNote(updatedNote) // 更新数据库
        if (ischeck) Toast.makeText(this, "已完成代办", Toast.LENGTH_LONG).show()
    }
    )

核心代码分析：

holder.todoCheckBox.isChecked = note.isDone：设置复选框的选中状态，这里将笔记的 isDone 属性（表示任务是否完成）绑定到复选框 todoCheckBox 的选中状态。如果 note.isDone 为 true，复选框会被选中。

if (note.isDone) holder.rootView.setBackgroundColor(Color.parseColor("#D4E2E6"))，else holder.rootView.setBackgroundColor(Color.parseColor("#FFFFFF"))：根据完成状态改变背景颜色，如果笔记的 isDone 属性为 true，说明笔记已完成，背景颜色会变为浅蓝
色（#D4E2E6），如果 isDone 为 false，背景色会保持白色（#FFFFFF）。

holder.itemView.setOnClickListener {onNoteClicked(note)}：这里设置了每个项的点击事件，当用户点击笔记时，会触发 onNoteClicked(note) 回调。这个回调一般会在外部传入，用于打开笔记的编辑页面等操作。

holder.todoCheckBox.setOnCheckedChangeListener {}：复选框状态变化的监听器，当复选框的选中状态发生变化时，触发此监听器。onCheckChanged(note, isChecked)：这个回调会在复选框状态改变时被调用。它会传递当前笔记和新的选中状态。通常在这个回调里会更新笔记的状态
（例如更新数据库中的 isDone 属性）。根据新的 isChecked 值，重新设置 rootView 的背景颜色。如果复选框选中（isChecked == true），背景会变成浅蓝色；如果复选框未选中（isChecked == false），背景会变回白色。

## 三、分类功能

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

核心代码:

    typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        noteType = when(position) {
            0 -> "学习"
            1 -> "工作"
            2 -> "生活"
            else -> "其他"
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

                val pos = when(it.type){
                "学习"->0
                "工作"->1
                "生活"->2
                else->0
            }

核心代码分析：

设置分类选择项：通过 when 语句，根据笔记的分类类型（it.type）设置 Spinner 的选项。

typeSpinner.onItemSelectedListener：为笔记分类选择的 Spinner 设置监听器。当用户选择不同的分类时，会调用 onItemSelected 方法。

onItemSelected：当用户选择一个分类时，这个方法会被触发。

position：表示用户选择的项的位置（从 0 开始）。

noteType：根据用户选择的 position，将 noteType 变量设置为相应的分类名：

0 -> "学习"

1 -> "工作"

2 -> "生活"

将 noteType 更新为用户选择的笔记类型，可能是 "学习", "工作", "生活" 。

onNothingSelected：和前面的 fontSizeSpinner 一样，这个方法在没有选择任何项时会被调用。在这个例子中同样没有定义具体行为，可以留空或做默认处理。

# 技术框架

## 1.布局主要选用线性布局，因为满足需求的情况下，此布局消耗资源少，部分选用约束布局

## 2.数据库的存储使用SQLite数据库，是Android原生自带的数据库，安全，高校

## 3.关于编辑器的字体和背景设置，采用sharedPreferences存储到本地，sp存储适合存储app的一些基础配置

# 优化点：

首页列表的NotesAdapter里，我选择在NoteViewHolder里进行view的绑定，避免了在onBindViewHolder进行绑定因为recycleView的缓存刷新机制，这样可以减少findViewById的IO操作，提高性能，减少内存消耗；
