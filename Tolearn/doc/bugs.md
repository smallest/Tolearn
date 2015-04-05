1. 使用Fragment的时候，Fragment中的onCreate()方法中“ View v = inflater.inflate(R.layout.fragment_log, container);”有问题，应改为“View v = inflater.inflate(R.layout.fragment_log, container，fasle)”，具体原因还不太清楚
2. ArrayAdapter的使用，对“public ArrayAdapter (Context context, int resource, T[] objects)”这个方法的使用有点问题，可以使用如“android.R.layout.simple_expandale_list_item_1”类似定义的内部的TextView
3. 创建的Activity类中的onCreate()方法，写成了Oncreate(),覆盖父类函数的时候还是应该在前面加"@Override"关键字啊，避免这样的错误
