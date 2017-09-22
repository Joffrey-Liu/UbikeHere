<h1>UbikeHERE</h1>
<h3>系統介紹<hr></h3>
<h4>隨著<a href="http://tycg.youbike.com.tw/cht/index.php">Ubike</a>的普及，愈來愈多民眾使用此來當作代步工具。然而，使用者卻無法及時且隨時地得知租賃站資訊，因此消耗人們許多時間在找尋停Ubike與車位；現在也有許多人騎著Ubike來當作運動因此也加入了個人化的卡路里消耗計算。</h4>
<h3>系統架構<hr></h3>
<img src="https://user-images.githubusercontent.com/11407961/30754106-fd8eb640-9ff3-11e7-9c25-c62b175301b0.JPG">
<h3>主要技術<hr></h3>

| 技術        | 技術介紹           
| ------------- |-------------| 
| Open data讀入與存取|使用URL下載，取得JSON檔資料，parsing所需資料(經緯度、站名、空車數量、可借車輛)並存入對應的資料結構。| 
| 標記車站位置與車站資訊|使用GoogleMap API將租賃車站的位置標記在地圖上，改寫資訊視窗來秀出站名、空車數量、可借車輛及平均等待時間。|   
| 紀錄喜愛租賃站 | 使用GoogleAPI onInfoWindowClick()來讓使用者可點擊資訊視窗並用Sharepreferences 來儲存使用者喜愛車站。使用ContextMenu 來秀出已儲存車站站名。      |   
| 導航功能|使用web版GooglemapAPI 取得兩點之間最短距離的路徑的每個節點的XML檔案，再利用map.polyline()將每個節點連接起來。| 
| 地圖搜尋功能|使用android.support.v7.widget.SearchView元件來實現搜尋功能，並將使用者所輸入的地點丟入geocoder.getFromLocationName()，且在地圖上標示出搜尋結果。|  
| 計算卡路里|使用Harris Benedict Equation（HBE）來計算出使用者一天的基礎代謝率。根據使用者騎車時間與使用者的身高體重來計算卡路里消耗量。| 
| 秀出天氣資訊|使用WWO(world weather onilne)取得桃園市天氣XML檔案，並parse XML檔案取得天氣資訊(氣溫、天氣描述)。| 
