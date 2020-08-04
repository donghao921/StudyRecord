## README
> Adapt 调用 notifyDataSetChanged() 方法刷新界面数据后，界面会自动滚动到顶部，解决方法：
1.recyclerView.setFocusableInTouchMode(false);
2.recyclerView.setFocusable(false);
3.setHasFixedSize(true);
总计：Recycleview不获取焦点即可解决此问题；但不知获取不到焦点是否会造成其他问题，待续。

