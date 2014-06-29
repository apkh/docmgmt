Hierarchical text is converted to numbered list as following:

1. Blah
Some internals
  1.1 Blah-Blah
  Some other
  1.2 Foo-foo
  Blah
2. FooBar
  2.1 Moo
  
Parsing is done from the same format using the simple rules:
First level hierarchy paragraphs start with regexp /s*/d+\.

Others levels are either:
* Continuation of current level list, e.g. 2.2->2.3, 1.4.5->1.4.6
* Next level: 2.2->2.2.1; 1.4.5->1.4.5.1
* One of previous level: 2.2->3, 1.4.5->1.5, 1.4.5->2

Everything between paragraphs are paragraph's body

Known bugs:
#1 Empty content exported as blank line instead of no line
#2 Multiline content exported with wrong indentation for lines after first line
