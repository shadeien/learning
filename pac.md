1. 获取gfwlist.txt, 最新地址
https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt.

```
wget https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt
```

2. 安装gfwlist2pac
```
pip install gfwlist2pac
```

3. 转pac
```
gfwlist2pac -i pac.txt -f gfwlist.js -p "SOCKS5 127.0.0.1:1080;"
```