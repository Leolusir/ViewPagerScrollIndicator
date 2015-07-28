# ViewPagerScrollIndicator

a simple library that help you create a pagerIndicator which also can be scrolled.

any discuss will be welcome : leo.lusir@gmail.com

## TODO

![image](https://github.com/Leolusir/ViewPagerScrollIndicator/blob/master/images/image-1.png) ![image](https://github.com/Leolusir/ViewPagerScrollIndicator/blob/master/images/image-2.png)

## How to use

  in __xml__:
  
  ```
  <com.leo.viewpagerscrollindicator.library.TabIndicator
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:scrollbars="none">

    </com.leo.viewpagerscrollindicator.library.TabIndicator>
  ```
  in __java__:
  
  ```
  indicator = (TabIndicator)findViewById(R.id.indicator);
  indicator.setWidth(width); // the width of your indicator
  indicator.setTabs(List<String>); // the titles of all tabs
  indicator.setViewPager(viewPager); // set the viewPager then will auto add a PageChangedListener inside
  indicator.setOnPageChangeListener(this); // also provide a interface let you do anything
  indicator.setOnItemClickListener(this); // click tab event
  ```

## Tips

in TabIndicator.class :
  Variable ｀visibleCount｀ control the visible tab count.
  
  in method `setPagerChangerListener()`, 
    `onPageScrolled(params...)` control the baseLine, 
    `onPagedSeleted(params...)` control the indicator and viewpager, `TabIndicator.this.smoothScrollTo(params...)` determines the seleted tab scroll to which position
    

## License

```
Copyright(c) 2015 leo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## other info
 `little_ming` is my Chinese name, ahaha.
