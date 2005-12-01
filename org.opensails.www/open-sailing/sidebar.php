			<div class="LeftColumn">
				<div id="Navigation">
					<ul>
						<!-- <li><a href="#">About</a> -->
						<li id="CurrentSection"><a href="http://blog.opensails.org/">Blog</a>
						<li><a href="http://trac.opensails.org/">Community</a>
						<!-- <li><a href="#">Download</a> -->
					</ul>
				</div>


				<div id="SubNavigation">	
					<div class="break">			
						<ul>
							<li>
								<!-- <?php include (TEMPLATEPATH . '/searchform.php'); ?> -->
							</li>
				
							<li>
							<?php /* If this is a 404 page */ if (is_404()) { ?>
							<?php /* If this is a category archive */ } elseif (is_category()) { ?>
							<p>You are currently browsing the archives for the <?php single_cat_title(''); ?> category.</p>
							
							<?php /* If this is a yearly archive */ } elseif (is_day()) { ?>
							<p>You are currently browsing the <a href="<?php echo get_settings('siteurl'); ?>"><?php echo bloginfo('name'); ?></a> weblog archives
							for the day <?php the_time('l, F jS, Y'); ?>.</p>
							
							<?php /* If this is a monthly archive */ } elseif (is_month()) { ?>
							<p>You are currently browsing the <a href="<?php echo get_settings('siteurl'); ?>"><?php echo bloginfo('name'); ?></a> weblog archives
							for <?php the_time('F, Y'); ?>.</p>
				
				 		    <?php /* If this is a yearly archive */ } elseif (is_year()) { ?>
							<p>You are currently browsing the <a href="<?php echo get_settings('siteurl'); ?>"><?php echo bloginfo('name'); ?></a> weblog archives
							for the year <?php the_time('Y'); ?>.</p>
							
							<?php /* If this is a monthly archive */ } elseif (is_search()) { ?>
							<p>You have searched the <a href="<?php echo get_settings('siteurl'); ?>"><?php echo bloginfo('name'); ?></a> weblog archives
							for <strong>'<?php echo wp_specialchars($s); ?>'</strong>. If you are unable to find anything in these search results, you can try one of these links.</p>
				
							<?php /* If this is a monthly archive */ } elseif (isset($_GET['paged']) && !empty($_GET['paged'])) { ?>
							<p>You are currently browsing the <a href="<?php echo get_settings('siteurl'); ?>"><?php echo bloginfo('name'); ?></a> weblog archives.</p>
				
							<?php } ?>
							</li>
						</ul>
					</div>
		
					<?php wp_list_pages('title_li=<h2>Pages</h2>' ); ?>

					<div class="break">
						<h4>Blog Archives</h4>
						<ul>
							<?php wp_get_archives('type=monthly'); ?>
						</ul>	
					</div>
					<h4>Blog Categories</h4>
					<ul>
						<?php wp_list_cats('sort_column=name&optioncount=1&hierarchical=0'); ?>
					</ul>										
				</div>	
				<div id="Tidbits">
					<?php include (TEMPLATEPATH . '/searchform.php'); ?>
					<?php /* If this is the frontpage */ if ( is_home() || is_page() ) { ?>				
						
				</div>								
						
            	<div id="Footer">
            		<!-- div class="break">
            			Copyright 2005 Adam Williams and Austin Taylor.
            		</div -->
	

						<?php get_links_list(); ?>
						
						<h4>Meta</h4>
						<ul>
							<?php wp_register(); ?>
							<li><?php wp_loginout(); ?></li>
							<li><a href="http://validator.w3.org/check/referer" title="This page validates as XHTML 1.0 Transitional">Valid <abbr title="eXtensible HyperText Markup Language">XHTML</abbr></a></li>
							<li><a href="http://gmpg.org/xfn/"><abbr title="XHTML Friends Network">XFN</abbr></a></li>
							<li><a href="http://wordpress.org/" title="Powered by WordPress, state-of-the-art semantic personal publishing platform.">WordPress</a></li>
							<?php wp_meta(); ?>
						</ul>
						</li>
					<?php } ?>

            	</div>
            </div>