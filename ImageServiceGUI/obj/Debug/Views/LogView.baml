   M S B A M L   `   `   `  ����J  FImageServiceGUI, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null=#clr-namespace:ImageServiceGUI.ViewsImageServiceGUI.Views  5      =#clr-namespace:ImageServiceGUI.ThemeImageServiceGUI.Theme  5      #    ImageServiceGUI.Views.LogView   5      \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5      R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5      Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5      @d2http://schemas.microsoft.com/expression/blend/2008     5      5local#clr-namespace:ImageServiceGUI.Views     5      ?brushConvertors#clr-namespace:ImageServiceGUI.Theme     5        � 	Resources  5
      /  )ImageServiceGUI.Theme.TypesColorConvertor   	ColorType��%   &	          5   
   6.   5      .��5      � .+�5   
   �� .+�5      �� $	��Log��6   $��22(�6"   $��DemiBold"�60   
���""��$��Center=�6]    J� RenderTransformOrigin$ 2.5,1&�6z   &   System.Windows.Controls.DataGrid  5      $��2��6      AutoGenerateColumns .  $	��210��6c      CanUserAddRows .     
IsReadOnly . ��. $
��Auto=�5      $
��Auto=�5      ��5   $   �� *
VM_AllLogs+   Columns 5      0  *System.Windows.Controls.DataGridTextColumn  5      ,  &System.Windows.Controls.DataGridColumn   Header$
 Type��6)      
FontWeight$
 Bold"�6S      
IsReadOnly . 1  +System.Windows.Controls.DataGridBoundColumn	   Binding	 67   �� *	logType+
   ElementStyle
 5      �� 5        �� 
TargetType" ��6$   .�5!   "   ��  �� Property � �� �� Value 6?   �� *	logType+ �� 	Converter# [  5"      5$        5%      $ Message��6)      Width5  /System.Windows.Controls.DataGridLengthConverter$ 1* 6V    . 	 6:   �� *	logInfo+6a   5'      5)      5*      5+      